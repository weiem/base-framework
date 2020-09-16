package com.wei.base.springframework.core.swagger;

import com.google.common.collect.Maps;
import com.wei.base.springframework.core.web.config.ResponseHandlerConfigure;
import com.wei.base.springframework.util.StringUtil;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import springfox.documentation.oas.mappers.ServiceModelToOpenApiMapperImpl;
import springfox.documentation.service.Documentation;

import java.beans.PropertyDescriptor;
import java.util.Map;

/**
 * swagger页面展示包装类封装
 *
 * @author : weierming
 * @date : 2020/9/16
 */
@Primary
@Component
public class Swagger3ServiceModelToOpenApiMapperImpl extends ServiceModelToOpenApiMapperImpl {

    @Autowired
    private ResponseHandlerConfigure responseHandlerConfigure;

    // 返回包装对象类型
    private static final String RESTFUL_SCHEMA_TYPE = "object";

    // 请求路径对象地址
    private static final String COMPONENTS_SCHEMAS_PATH = "#/components/schemas/%s";

    @Override
    public OpenAPI mapDocumentation(Documentation from) {
        OpenAPI openAPI = super.mapDocumentation(from);
        Components components = openAPI.getComponents();
        Map<String, Schema> schemas = components.getSchemas();

        Class<?> restfulClass = responseHandlerConfigure.getRestfulVO().getClass();
        Map<String, String> pathsResponsesObjectMap = getPathsResponsesObjectMap(schemas, restfulClass);
        replacePathsPathsResponsesObject(openAPI.getPaths(), pathsResponsesObjectMap, restfulClass);
        return openAPI;
    }

    /**
     * 获取路径返回对象转换ma
     *
     * @param schemas      Components中的schemas
     * @param restfulClass 返回对象class
     * @return key 路径原对象 value 封装后的对象
     */
    private Map<String, String> getPathsResponsesObjectMap(Map<String, Schema> schemas, Class<?> restfulClass) {
        if (MapUtils.isEmpty(schemas)) {
            return Maps.newHashMap();
        }

        Map<String, String> map = Maps.newHashMapWithExpectedSize(schemas.size());
        String restfulVOName = responseHandlerConfigure.getRestfulVOName();
        for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
            Schema schema = entry.getValue();
            // 判断返回对象是否已经包含默认包装类格式
            if (StringUtil.containsIgnoreCase(entry.getKey(), restfulVOName)) {
                continue;
            }

            // 判断对象集合里该对象是否已存在
            String restfulSchemaName = restfulVOName + String.format("«%s»", schema.getName());
            String schemaName = String.format(COMPONENTS_SCHEMAS_PATH, schema.getName());
            map.put(schemaName, String.format(COMPONENTS_SCHEMAS_PATH, restfulSchemaName));
            // 判断转换的对象在Components的schemas中是否存在,如果存在则不用继续往schemas中添加对象
            if (schemas.containsKey(restfulSchemaName)) {
                continue;
            }

            Schema restfulSchema = getRestfulSchema(schema, restfulClass);
            schemas.put(restfulSchemaName, restfulSchema);
        }

        return map;
    }

    /**
     * 获取自定义封装返回的schema对象
     *
     * @param schema       原路径返回的schema
     * @param restfulClass 返回对象类
     * @return 自定义封装返回的schema对象
     */
    private Schema getRestfulSchema(Schema schema, Class<?> restfulClass) {
        // 通过反射获取返回对象的属性
        PropertyDescriptor[] propertyDescriptors = ReflectUtils.getBeanProperties(restfulClass);
        Schema restfulSchema = new Schema();
        if (propertyDescriptors == null) {
            return restfulSchema;
        }

        Map<String, Schema> restfulVOProperties = Maps.newHashMap();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String name = propertyDescriptor.getName();
            // 如果schema为空则把data去掉只返回状态码和内容
            if (schema == null && StringUtil.equals(name, "data")) {
                continue;
            }

            Schema restfulPropertiesSchema = new Schema();

            restfulPropertiesSchema.setName(name);
            restfulPropertiesSchema.setTitle(name);
            String typeName = propertyDescriptor.getPropertyType().getTypeName();
            restfulPropertiesSchema.setType(StringUtil.lowerCase(StringUtil.getClassName(typeName)));
            restfulVOProperties.put(name, restfulPropertiesSchema);
        }

        // 返回自定义返回对象名称
        String restfulSchemaName = StringUtil.getClassName(restfulClass.getTypeName());
        if (schema != null) {
            // 拼接泛型对象
            restfulSchemaName += String.format("«%s»", schema.getName());
        }

        restfulSchema.setName(restfulSchemaName);
        restfulSchema.setTitle(restfulSchemaName);
        restfulSchema.setType(RESTFUL_SCHEMA_TYPE);
        restfulSchema.setProperties(restfulVOProperties);
        return restfulSchema;
    }

    /**
     * 替换 路径中的Responses返回对象
     *
     * @param paths                   路径对象
     * @param pathsResponsesObjectMap 路径替换对象map,key 路径原对象 value 封装后的对象
     * @param restfulClass            返回对象class
     */
    private void replacePathsPathsResponsesObject(Paths paths, Map<String, String> pathsResponsesObjectMap,
                                                  Class<?> restfulClass) {
        if (MapUtils.isEmpty(paths)) {
            return;
        }

        for (Map.Entry<String, PathItem> mapEntry : paths.entrySet()) {
            Operation operation = getOperation(mapEntry.getValue());
            if (operation == null) {
                continue;
            }

            Content content = getContent(operation, restfulClass);
            if (content == null) {
                continue;
            }

            for (Map.Entry<String, MediaType> contentEntry : content.entrySet()) {
                Schema mediaTypeSchema = contentEntry.getValue().getSchema();
                String ref = mediaTypeSchema.get$ref();
                if (!pathsResponsesObjectMap.containsKey(ref)) {
                    continue;
                }

                mediaTypeSchema.set$ref(pathsResponsesObjectMap.get(ref));
            }
        }
    }

    /**
     * 根据请求方式获取请求地址对应的Operation
     *
     * @param pathItem 路径对象
     * @return 返回路径对用的请求操作对象
     */
    private Operation getOperation(PathItem pathItem) {
        if (pathItem.getGet() != null) {
            return pathItem.getGet();
        } else if (pathItem.getPost() != null) {
            return pathItem.getPost();
        } else if (pathItem.getPut() != null) {
            return pathItem.getPut();
        } else if (pathItem.getDelete() != null) {
            return pathItem.getDelete();
        } else if (pathItem.getOptions() != null) {
            return pathItem.getOptions();
        } else if (pathItem.getHead() != null) {
            return pathItem.getHead();
        } else if (pathItem.getPatch() != null) {
            return pathItem.getPatch();
        } else if (pathItem.getTrace() != null) {
            return pathItem.getTrace();
        } else {
            return null;
        }
    }

    /**
     * 获取Content
     *
     * @param operation
     * @return
     */
    private Content getContent(Operation operation, Class<?> restfulClass) {
        if (operation == null) {
            return null;
        }

        ApiResponses responses = operation.getResponses();
        ApiResponse apiResponse = responses.get(String.valueOf(HttpStatus.OK.value()));
        Content content = apiResponse.getContent();
        // 方法放回viod时content会为空
        if (content.isEmpty()) {
            MediaType mediaType = new MediaType();
            mediaType.schema(getRestfulSchema(null, restfulClass));

            // 添加返回对象
            content.addMediaType(org.springframework.http.MediaType.ALL_VALUE, mediaType);
        }

        return content;
    }
}