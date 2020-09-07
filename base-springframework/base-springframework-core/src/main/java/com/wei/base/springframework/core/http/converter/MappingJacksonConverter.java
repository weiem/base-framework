package com.wei.base.springframework.core.http.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.wei.base.springframework.core.web.config.ResponseHandlerConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;

@Configuration
public class MappingJacksonConverter {

    @Autowired
    private ResponseHandlerConfigure responseHandlerConfigure;

    @Bean
    public MappingJackson2HttpMessageConverter configureMessageConverters() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        //设置日期格式
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(responseHandlerConfigure.getDateFormat());
        objectMapper.setDateFormat(simpleDateFormat);

        // null值是否返回,默认为不返回
        if (responseHandlerConfigure.getIsWriteMapNullValue()) {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new StringWithoutSpaceDeserializer(String.class));
        objectMapper.registerModule(module);

        //在反序列化时忽略在 json 中存在但 Java 对象不存在的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        mappingJackson2HttpMessageConverter.setDefaultCharset(Charsets.UTF_8);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON));
        return mappingJackson2HttpMessageConverter;
    }
}