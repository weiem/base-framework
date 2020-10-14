package com.wei.gateway.filter;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 限流器
 *
 * @author : weierming
 * @date : 2020/10/9
 */
@Component
public class LimiterFilter implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        // 根据IP + 请求地址过滤
        return Mono.just(request.getRemoteAddress().getHostName() + ":" + request.getPath());
    }
}