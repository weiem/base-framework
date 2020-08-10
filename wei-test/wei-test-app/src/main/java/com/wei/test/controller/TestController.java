package com.wei.test.controller;

import com.wei.base.springframework.constant.vo.RestfulVO;
import com.wei.cloud.api.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private ApiService apiService;

    @Value("${test}")
    private String test;

    @GetMapping("/test")
    public String test() {
        return test;
    }

    @GetMapping("/testCloud")
    public String testCloud() {
        return apiService.test("true");
    }

    @PostMapping("/test2/{ttt}")
    public RestfulVO<String> test2(@PathVariable("ttt") String ttt) {
        System.err.println(ttt);
        return new RestfulVO("test");
    }
}