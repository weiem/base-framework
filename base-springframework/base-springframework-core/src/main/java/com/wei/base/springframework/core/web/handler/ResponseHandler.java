package com.wei.base.springframework.core.web.handler;

import com.wei.base.springframework.constant.vo.RestfulVO;
import com.wei.base.springframework.core.web.config.ResponseHandlerConfigure;
import com.wei.base.springframework.util.RegexUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 返回值封装
 *
 * @author : weierming
 * @date : 2020/7/10
 */
@Slf4j
@ControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice<Object> {

    @Autowired
    private ResponseHandlerConfigure responseHandlerConfigure;

    // 只对以下路径进行封装 以防封装到spring以及第三方接口导致出现问题情况
    private static final String PACKAGE_PATH = ".*.controller.*";

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果没启用的话就跳过
        if (!responseHandlerConfigure.getEnabled()) {
            return Boolean.FALSE;
        }

        // 如果不是controller包下的不进行封装 以防封装到spring以及第三方接口导致出现问题情况
        if (!RegexUtil.matcher(PACKAGE_PATH, methodParameter.getExecutable().getDeclaringClass().getName())) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    @Override
    public Object beforeBodyWrite(Object t, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> httpMessageConverter,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        return new RestfulVO<>(t);
    }
}