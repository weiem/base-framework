package com.wei.base.springframework.cloud.feign;

import feign.FeignException;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * feign出参返回值
 *
 * @author : weierming
 * @date : 2020/7/24
 */
@Slf4j
@Configuration
public class FeignDecoder extends SpringDecoder {

    public FeignDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        super(messageConverters);
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        String rString = String.valueOf(super.decode(response, String.class));
        log.info("url:{}, body:{}", response.request().url(), rString);
        return rString;
    }
}
