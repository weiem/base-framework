//package com.wei.base.springframework.core.config;
//
//import io.swagger.models.Swagger;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import springfox.documentation.service.Documentation;
//import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2MapperImpl;
//
//@Primary
//@Configuration
//public class MyServiceModelToSwagger2Mapper extends ServiceModelToSwagger2MapperImpl {
//
//    /**
//     * 是否需要封装swagger返回值
//     */
//    @Override
//    public Swagger mapDocumentation(Documentation from) {
//        from.getApiListings()
//        Swagger swagger = super.mapDocumentation(from);
//        return swagger;
//    }
//}