package com.wei.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "wei-test-cloud")
@RequestMapping("/apiService")
public interface ApiService {

    @GetMapping("/test")
    String test(@RequestHeader("Filter-Enabled") String filterEnabled);
}