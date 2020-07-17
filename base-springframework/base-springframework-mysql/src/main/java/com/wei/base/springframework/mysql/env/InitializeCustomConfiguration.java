package com.wei.base.springframework.mysql.env;

import com.google.common.collect.Lists;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * 加载自定义配置文件
 *
 * @author : weierming
 * @date : 2020/7/13
 */
public class InitializeCustomConfiguration implements EnvironmentPostProcessor {

    //Properties对象
    private final Properties PROPERTIES = new Properties();

    //自定义配置文件地址
    private static final List<String> PROFILES = Lists.newArrayList("mysql-application.yml");

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        PROFILES.stream().forEach(profile -> {
            //从classpath路径下面查找文件
            Resource resource = new ClassPathResource(profile);
            //加载成PropertySource对象，并添加到Environment环境中
            environment.getPropertySources().addLast(loadProfiles(resource));
        });
    }

    //加载单个配置文件
    private PropertySource<?> loadProfiles(Resource resource) {
        if (resource == null || !resource.exists()) {
            throw new IllegalArgumentException("资源" + resource + "不存在");
        }

        try {
            //从输入流中加载一个Properties对象
            PROPERTIES.load(resource.getInputStream());
            return new PropertiesPropertySource(resource.getFilename(), PROPERTIES);
        } catch (IOException ex) {
            throw new IllegalStateException("加载配置文件失败" + resource, ex);
        }
    }
}