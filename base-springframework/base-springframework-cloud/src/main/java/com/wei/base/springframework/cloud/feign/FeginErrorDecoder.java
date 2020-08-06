package com.wei.base.springframework.cloud.feign;

import com.google.common.base.Charsets;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static feign.FeignException.errorStatus;

/**
 * feign错误拦截器
 *
 * @author : weierming
 * @date : 2020/7/24
 */
@Slf4j
public class FeginErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String body = Util.toString(response.body().asReader(Charsets.UTF_8));
            log.error("body:{}", body);
        } catch (IOException e) {
            log.error("读取body失败!", e);
        }

        FeignException e = errorStatus(methodKey, response);
        log.error("request:{}", response.request(), e);
        Exception exception = null;
        switch (response.status()) {
            case 404:
                exception = new Exception("服务不存在!");

            default:
                break;
        }

        return exception;
    }
}