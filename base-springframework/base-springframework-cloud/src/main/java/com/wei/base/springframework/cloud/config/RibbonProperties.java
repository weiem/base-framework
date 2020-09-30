package com.wei.base.springframework.cloud.config;

import com.wei.base.springframework.cloud.vo.EagerLoad;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Data
@Configuration
@ConfigurationProperties(prefix = "ribbon")
public class RibbonProperties implements Serializable {

    private static final long serialVersionUID = -2085296475361063649L;

    /**
     * 懒加载配置
     */
    private EagerLoad eagerLoad;
}