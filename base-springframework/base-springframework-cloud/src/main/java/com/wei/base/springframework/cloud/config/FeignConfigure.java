package com.wei.base.springframework.cloud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "spring.response-body-advice")
@Configuration
public class FeignConfigure {

    /**
     * 过滤header参数传递字段
     */
    private String headerTransmitFilter = "Filter-Enabled";
}
