//package com.wei.gateway.filter;
//
//import com.alibaba.fastjson.JSONArray;
//import com.honglinktech.zbgj.cloud.gateway.constants.RedisKeyConstant;
//import com.honglinktech.zbgj.cloud.gateway.constants.ServletConstants;
//import com.honglinktech.zbgj.cloud.gateway.enums.GatewayExceptionEnum;
//import com.honglinktech.zbgj.springframework.core.vo.UserSession;
//import com.honglinktech.zbgj.springframework.util.RegexUtil;
//import com.honglinktech.zbgj.springframework.util.StringUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.collections4.MapUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import javax.annotation.Resource;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//@Component
//public class AuthSignatureFilter implements GlobalFilter, Ordered {
//
//    @Resource
//    RedisTemplate<String, Object> redisTemplate;
//
//    @Value("${filter.path}")
//    private String filterPath;
//
//    @Value("${login.invalidTime}")
//    private Long loginInvalidTime;
//
//    @Value("${apiToken}")
//    private String apiToken;
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest serverHttpRequest = exchange.getRequest();
//
//        String path = serverHttpRequest.getPath().pathWithinApplication().value();
//        HttpHeaders httpHeaders = serverHttpRequest.getHeaders();
//        UserSession userSession = new UserSession();
//        ServerHttpResponse serverHttpResponse = exchange.getResponse();
//
//        // 判断url是否需要过滤
//        if (!RegexUtil.matcher(filterPath, path)) {
//            List<String> tokenHeaders = httpHeaders.get(ServletConstants.X_AUTH_TOKEN);
//
//            // 获取token 判断是否存在
//            if (CollectionUtils.isEmpty(tokenHeaders) || StringUtil.isBlank(tokenHeaders.get(0))) {
//                return getErrorMsg(serverHttpResponse, GatewayExceptionEnum.LOGON_FAILURE);
//            }
//
//            String token = tokenHeaders.get(0);
//            if (!StringUtil.equals(token, apiToken)) {
//                try {
//                    log.info("token:{}", token);
//                    // 从redis中获取token 判断是否存在 如果不存在则提示错误
//                    userSession = (UserSession) redisTemplate.opsForValue().get(RedisKeyConstant.USER_TOKEN + token);
//                    if (userSession == null) {
//                        return getErrorMsg(serverHttpResponse, GatewayExceptionEnum.LOGON_FAILURE);
//                    }
//
//                    // 延长redis失效时间
//                    redisTemplate.expire(RedisKeyConstant.USER_TOKEN + token, loginInvalidTime, TimeUnit.SECONDS);
//                } catch (Exception e) {
//                    log.info(e.getMessage(), e);
//                    return getErrorMsg(serverHttpResponse, GatewayExceptionEnum.SYSTEM_ERROR);
//                }
//            }
//
//            // 判断用户是否有权限访问
//            List<String> sourceHeaders = httpHeaders.get(ServletConstants.X_AUTH_SOURCE);
//            if (CollectionUtils.isNotEmpty(sourceHeaders)) {
//                Object object = redisTemplate.opsForValue()
//                        .get(RedisKeyConstant.USER_ROLE + userSession.getUserId().toString());
//
//                if (object == null) {
//                    return getErrorMsg(serverHttpResponse, GatewayExceptionEnum.PERMISSION_IS_NULL);
//                }
//
//                Map<String, List<String>> map = (Map<String, List<String>>) object;
//                if (MapUtils.isEmpty(map) || CollectionUtils.isEmpty(map.get(path))) {
//                    return getErrorMsg(serverHttpResponse, GatewayExceptionEnum.PERMISSION_IS_NULL);
//                }
//
//                String source = sourceHeaders.get(0);
//                List<String> sources = map.get(path);
//                if (!sources.contains(source)) {
//                    return getErrorMsg(serverHttpResponse, GatewayExceptionEnum.PERMISSION_IS_NULL);
//                }
//            }
//        }
//
//        log.info("request = {}", JSONArray.toJSONString(exchange.getRequest().getSslInfo()));
//
//        // 如果header中已存在 user-session 则以header中的为主
//        serverHttpRequest.mutate().header(ServletConstants.USER_SESSION, JSONArray.toJSONString(userSession));
//        ServerWebExchange build = exchange.mutate().request(serverHttpRequest).build();
//        return chain.filter(build);
//    }
//
//    /**
//     * 封装异常用于gateway过滤器返回
//     *
//     * @param serverHttpResponse
//     * @param gatewayExceptionEnum
//     * @return
//     */
//    private Mono<Void> getErrorMsg(ServerHttpResponse serverHttpResponse, GatewayExceptionEnum gatewayExceptionEnum) {
//        serverHttpResponse.setStatusCode(HttpStatus.OK);
//        serverHttpResponse.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
//
//        String body = String.format("{\"code\":\"%s\",\"msg\":\"%s\"}", gatewayExceptionEnum.getCode(),
//                gatewayExceptionEnum.getMsg());
//
//        log.info(body);
//        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
//        DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(bytes);
//
//        Mono<DataBuffer> mono = Mono.just(buffer);
//        return serverHttpResponse.writeWith(mono);
//    }
//
//    @Override
//    public int getOrder() {
//        return -200;
//    }
//}