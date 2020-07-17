//package com.wei.base.springframework.core.config;
//
//import com.google.common.collect.Lists;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.SwaggerDefinition;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.List;
//
//@SwaggerDefinition
//@EnableSwagger2
//@Configuration
//public class Swagger2Config {
//
//    @Bean
//    public Docket createRestApi() {
//        List<Parameter> pars = Lists.newArrayList();
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        // header中的token参数非必填，传空也可以
//        tokenPar.name("x-auth-token").modelRef(new ModelRef("string")).
//                parameterType("header").required(false).build();
//        pars.add(tokenPar.build());
//
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .paths(PathSelectors.any()).build().globalOperationParameters(pars);
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title("Spring Boot中使用Swagger2构建RESTful APIs").description("字母大小写转换")
//                .termsOfServiceUrl("https://www.iamwawa.cn/daxiaoxie.html").version("1.0").license("服务监控")
//                .licenseUrl("/monitoring").build();
//    }
//}