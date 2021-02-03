package com.wei.base.springframework.cloud.config;

import com.google.common.collect.Lists;
import com.wei.base.springframework.cloud.vo.EagerLoad;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonEagerLoadProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RibbonConfig {

    @Autowired
    private ConsulDiscoveryClient consulDiscoveryClient;

    @Autowired
    private RibbonEagerLoadProperties ribbonEagerLoadProperties;

    @Autowired
    private RibbonProperties ribbonProperties;

    /**
     * 全局自动懒加载
     */
    @Bean
    @ConditionalOnProperty("ribbon.eager-load.enabled")
    public void autoEagerLoad() {
        //判断是否需要自动加载
        EagerLoad eagerLoad = ribbonProperties.getEagerLoad();
        if (eagerLoad == null && !eagerLoad.getAutoEnabled()) {
            return;
        }

        // 在服务启动后读取consul中的注册列表缓存起来减少自定义配置
        List<ServiceInstance> serviceInstances = consulDiscoveryClient.getAllInstances();
        List<String> clients = Lists.newArrayListWithCapacity(serviceInstances.size());

        List<String> excludes = eagerLoad.getExcludes();
        for (ServiceInstance serviceInstance : serviceInstances) {
            String serviceId = serviceInstance.getServiceId();
            if (CollectionUtils.isNotEmpty(excludes)) {
                if (excludes.contains(serviceId)) {
                    continue;
                }
            }

            clients.add(serviceId);
        }

        // 重新修改ribbon懒加载配置
        ribbonEagerLoadProperties.setClients(clients);
    }
}