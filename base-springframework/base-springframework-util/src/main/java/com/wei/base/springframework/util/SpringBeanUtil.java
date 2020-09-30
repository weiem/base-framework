package com.wei.base.springframework.util;

import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringBeanUtil implements ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 初始化applicationContext
        SpringBeanUtil.applicationContext = applicationContext;
    }

    /**
     * 通过类获取
     *
     * @param clazz 注入的类
     * @param <T>   返回类型
     * @return 返回这个bean
     * @throws BeansException bean异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<?> clazz) throws BeansException {
        return (T) applicationContext.getBean(clazz);
    }

    /**
     * 通过名字获取
     *
     * @param name 名字
     * @param <T>  返回类型
     * @return 返回这个bean
     * @throws BeansException bean异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 给实例化的类注入需要的bean (@Autowired) 如果不注入，被@Autowired注解的变量会报空指针
     *
     * @param t
     */
    public static <T> void autowireBean(T t) {
        applicationContext.getAutowireCapableBeanFactory().autowireBean(t);
    }

    public static List<String> getActiveProfile() {
        String[] profiles = applicationContext.getEnvironment().getActiveProfiles();
        if (profiles != null) {
            return Lists.newArrayList(profiles);
        }

        return Lists.newArrayList();
    }
}