package com.wei.base.springframework.core.http.converter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.wei.base.springframework.core.web.config.ResponseHandlerConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

@Configuration
public class FastJsonConverter {

    @Autowired
    private ResponseHandlerConfigure responseHandlerConfigure;

    @Bean
    public HttpMessageConverter<Object> configureMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setCharset(Charsets.UTF_8);
        fastJsonConfig.setDateFormat(responseHandlerConfigure.getDateFormat());

        // null值是否返回,默认为不返回
        if (responseHandlerConfigure.getIsWriteMapNullValue()) {
            fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        }

        fastConverter.setFastJsonConfig(fastJsonConfig);
        fastConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON));
        return fastConverter;
    }
}