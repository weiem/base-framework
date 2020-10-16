package com.wei.base.springframework.mybatisplus.config;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = Constants.MYBATIS_PLUS)
public class CustomMybatisPlusProperties {

    /**
     * 每次插入的大小
     */
    private Integer batchSize;
}