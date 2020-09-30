package com.wei.base.springframework.cloud.feign;

import com.wei.base.springframework.cloud.config.FeignConfigure;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

/**
 * feign拦截器,主要用于往header中添加参数
 *
 * @author : weierming
 * @date : 2020/7/23
 */
@Configuration
public class FeignInterceptor implements RequestInterceptor {

    @Autowired
    private FeignConfigure feignConfigure;

    /**
     * 封装fegin header参数传递
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
            Map<String, Collection<String>> headers = template.headers();

            // 有些第三方接口有做安全校验如果header传递的话会导致报错
            String headerTransmitFilter = feignConfigure.getHeaderTransmitFilter();
            if (headers.containsKey(headerTransmitFilter)) {
                Collection<String> key = headers.get(headerTransmitFilter);
                if (key.contains("true")) {
                    return;
                }
            }

            Enumeration<String> headNames = httpServletRequest.getHeaderNames();
            while (headNames.hasMoreElements()) {
                String headName = headNames.nextElement();
                template.header(headName, httpServletRequest.getHeader(headName));
            }
        }
    }
}