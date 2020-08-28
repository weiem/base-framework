package com.wei.gateway.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class GatewayConfigure {

    @Value("${filter.path}")
    private String filterPath;

    @Value("${login.invalidTime}")
    private Long loginInvalidTime;

    @Value("${login.token}")
    private String loginToken;
}