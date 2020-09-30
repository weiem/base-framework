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

    @Bean
    @ConditionalOnProperty("ribbon.eager-load.enabled")
    public void autoEagerLoad() {
        //判断是否需要自动加载
        EagerLoad eagerLoad = ribbonProperties.getEagerLoad();
        if (!eagerLoad.getAutoEnabled()) {
            return;
        }
        
        List<ServiceInstance> serviceInstances = consulDiscoveryClient.getAllInstances();
        List<String> clients = Lists.newArrayListWithCapacity(serviceInstances.size());

        List<String> excludes = Lists.newArrayList();
        if (eagerLoad != null) {
            excludes = eagerLoad.getExcludes();
        }

        for (ServiceInstance serviceInstance : serviceInstances) {
            String serviceId = serviceInstance.getServiceId();
            if (CollectionUtils.isNotEmpty(excludes)) {
                if (excludes.contains(serviceId)) {
                    continue;
                }
            }

            clients.add(serviceId);
        }

        ribbonEagerLoadProperties.setClients(clients);
    }
}