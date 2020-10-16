package com.wei.base.springframework.mybatisplus.env;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
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
public class InitializeCustomConfiguration implements EnvironmentPostProcessor, Ordered {

    //自定义配置文件地址
    private static final List<String> PROFILES = Lists.newArrayList("application-mybatis-plus.yml");

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        PROFILES.stream().forEach(profile -> {
            //从classpath路径下面查找文件
            Resource resource = new ClassPathResource(profile);
            if (resource == null || !resource.exists()) {
                throw new IllegalArgumentException("资源" + resource + "不存在");
            }

            MutablePropertySources mutablePropertySources = environment.getPropertySources();
            //加载成PropertySource对象，并添加到Environment环境中
            switch (StringUtils.substringAfterLast(profile, ".")) {
                case "yml":
                    List<PropertySource<?>> propertySources = loadYmlProfiles(resource);
                    propertySources.stream().forEach(propertySource -> {
                        mutablePropertySources.addLast(propertySource);
                    });
                    break;
                default:
                    mutablePropertySources.addLast(loadProfiles(resource));
                    break;
            }
        });
    }

    /**
     * 加载单个配置文件
     *
     * @param resource
     * @return
     */
    private PropertiesPropertySource loadProfiles(Resource resource) {
        try {
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            return new PropertiesPropertySource(resource.getFilename(), properties);
        } catch (IOException ex) {
            throw new IllegalStateException("加载配置文件失败" + resource, ex);
        }
    }

    /**
     * 加载yml格式配置文件
     *
     * @param resource
     * @return
     */
    private List<PropertySource<?>> loadYmlProfiles(Resource resource) {
        try {
            YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
            return yamlPropertySourceLoader.load(resource.getFilename(), resource);
        } catch (IOException ex) {
            throw new IllegalStateException("加载配置文件失败" + resource, ex);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}