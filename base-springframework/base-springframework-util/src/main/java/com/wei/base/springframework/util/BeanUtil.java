package com.wei.base.springframework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 对象拷贝工具类
 *
 * @author : weierming
 * @date : 2020/9/30
 */
@Slf4j
public class BeanUtil {

    /**
     * Map<source class,<target class , BeanCopier>>
     */
    private static final ConcurrentHashMap<Class<?>, ConcurrentHashMap<Class<?>, BeanCopier>> cache = new ConcurrentHashMap<>();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 转换为格式化的json
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // 如果json中有新增的字段并且是实体类类中不存在的，不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 浅拷贝，对象属性都是基本类型时使用， 性能最优
     */
    public static <T> T copy(Object source, Class<T> targetClass) {

        ConcurrentHashMap<Class<?>, BeanCopier> map = cache.get(source.getClass());
        if (map == null) {
            cache.putIfAbsent(source.getClass(), new ConcurrentHashMap<>());
            map = cache.get(source.getClass());
        }
        BeanCopier copier = map.get(targetClass);
        if (copier == null) {
            map.putIfAbsent(targetClass, BeanCopier.create(source.getClass(), targetClass, false));
            copier = map.get(targetClass);
        }
        T targetObj;
        try {
            targetObj = targetClass.newInstance();
            copier.copy(source, targetObj, null);
            return targetObj;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(targetClass.getName() + " could not initial ! ", e);
        }
    }


    /**
     * 列表，浅拷贝， 参数对象没有其他复杂数据类型时使用 , 性能比deepCopy高
     *
     * @param source      ，可以是单个对象或者是列表,如果是列表则返回List
     * @param targetClass ，返回的对象类型
     * @return 空列表或者是List(类型为targetClass)
     */
    public static <T, E> List<T> copy(List<E> source, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        }
        return source.stream().map(sourceObj -> copy(sourceObj, targetClass)).collect(Collectors.toList());

    }


    /**
     * 深拷贝,对象存在其它引用时候使用
     *
     * @param source      ，源对象
     * @param targetClass ，返回的对象类型
     * @return null 或者 targetClass
     */
    public static <T> T deepCopy(Object source, Class<T> targetClass) throws JsonProcessingException {
        if (source == null) {
            return null;
        }

        return objectMapper.readValue(objectMapper.writeValueAsString(source), targetClass);
    }


    /**
     * 深拷贝,对象存在其它引用时候使用
     *
     * @param source      ，源对象列表
     * @param targetClass ，返回的对象类型
     * @return 空列表 或者是List(类型为targetClass)
     */
    public static <T, E> List<T> deepCopy(List<E> source, Class<T> targetClass) throws JsonProcessingException {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        }

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, targetClass);
        return objectMapper.readValue(objectMapper.writeValueAsString(source), javaType);
    }

    /**
     * 将对象转换成Map
     *
     * @param bean
     * @return map ，key为属性名，如果属性为复杂类型，则为嵌套map
     */
    public static Map<String, Object> bean2Map(Object bean) {
        Map<String, Object> map = new HashMap<>(1);
        BeanMap beanMap = BeanMap.create(bean);
        Set<?> set = beanMap.keySet();
        for (Object key : set) {
            Object value = beanMap.get(key);
            bean2MapRecursion(map, value, key.toString());
        }

        return map;
    }


    private static void bean2MapRecursion(Map<String, Object> map, Object bean, String propertyName) {
        if (bean != null) {
            try {
                if (isPrimitive(bean.getClass())) {
                    map.put(propertyName, bean);
                } else if (Object[].class.isInstance(bean)) {
                    List<Map<String, Object>> list = new ArrayList<>();
                    for (Object item : (Object[]) bean) {
                        list.add(bean2Map(item));
                    }
                    map.put(propertyName, list);
                } else if (Collection.class.isInstance(bean)) {
                    List<Map<String, Object>> list = new ArrayList<>();
                    for (Object item : (Collection<?>) bean) {
                        list.add(bean2Map(item));
                    }
                    map.put(propertyName, list);
                } else if (Map.class.isInstance(bean)) {
                    Map<String, Object> subMap = new HashMap<>();
                    for (Map.Entry<?, ?> entry : ((Map<?, ?>) bean).entrySet()) {
                        bean2MapRecursion(subMap, entry.getValue(), entry.getKey().toString());
                    }
                    map.put(propertyName, subMap);
                } else {
                    Map<String, Object> subMap = new HashMap<>();
                    BeanMap beanMap = BeanMap.create(bean);
                    Set<?> set = beanMap.keySet();
                    for (Object key : set) {
                        Object value = beanMap.get(key);
                        bean2MapRecursion(subMap, value, key.toString());
                    }
                    map.put(propertyName, subMap);
                }
            } catch (RuntimeException e) {
                log.error("propertyName = {} , value = ", propertyName, bean);
                throw e;
            }
        }
    }

    /**
     * 是否基本类型
     *
     * @param cls
     * @return
     */
    public static boolean isPrimitive(Class<?> cls) {
        if (cls.isArray()) {
            return isPrimitive(cls.getComponentType());
        }

        return cls.isPrimitive() || cls == Class.class || cls == String.class || cls == Boolean.class || cls == Character.class || Number.class.isAssignableFrom(cls)
                || Date.class.isAssignableFrom(cls);
    }
}