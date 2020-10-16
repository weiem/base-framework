package com.wei.base.springframework.swagger.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger页面配置
 * 如果想关闭swagger需要在对应的配置文件中加上
 * springfox.documentation.swagger-ui.enabled: false
 * 即可
 *
 * @author : weierming
 * @date : 2020/9/16
 */
@Configuration
public class Swagger3Config {

    @Autowired
    private ConfigurableEnvironment env;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(env.getProperty("spring.application.name") + "接口文档")
                .description("所有返回数据都会包含{\"code\" : 200, \"message\" : \"test\", \"data\": Responses}")
                .contact(new Contact("prinz wei",
                        "https://github.com/weiem/base-framework",
                        "799058254@qq.com"))
                .version("1.0")
                .build();
    }
}