package com.wei.base.springframework.core.web.config;

import com.wei.base.springframework.constant.vo.RestfulVO;
import com.wei.base.springframework.util.StringUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "spring.response-body-advice")
@Configuration
public class ResponseHandlerConfigure {

    /**
     * 是否启用返回值封装 默认为true
     */
    private Boolean enabled = Boolean.TRUE;

    /**
     * null值是否返回,默认为不返回
     */
    private Boolean isWriteMapNullValue = Boolean.FALSE;

    /**
     * 返回值时间格式
     */
    private String dateFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * 返回值时间格式
     */
    private RestfulVO restfulVO = new RestfulVO();

    public String getRestfulVOClassName() {
        return restfulVO.getClass().getName();
    }

    public String getRestfulVOName() {
        return StringUtil.getClassName(getRestfulVOClassName());
    }
}