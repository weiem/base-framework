package com.wei.base.springframework.util;

import com.google.common.collect.Lists;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ReflectionUtil extends ReflectionUtils {

    /**
     * 获取继承类中的泛型
     *
     * @param clazz 需要获取类的class
     * @return 泛型class
     */
    public static List<Class<?>> getSuperGenericityClass(Class<?> clazz) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        if (genericInterfaces.length == 0) {
            return null;
        }

        Type genericInterface = genericInterfaces[0];
        if (!ParameterizedType.class.isAssignableFrom(genericInterface.getClass())) {
            return null;
        }

        Type[] actualTypeArguments = ((ParameterizedType) genericInterface).getActualTypeArguments();
        List<Class<?>> list = Lists.newArrayListWithExpectedSize(actualTypeArguments.length);
        for (Type actualTypeArgument : actualTypeArguments) {
            list.add((Class<?>) actualTypeArgument);
        }

        return list;
    }
}