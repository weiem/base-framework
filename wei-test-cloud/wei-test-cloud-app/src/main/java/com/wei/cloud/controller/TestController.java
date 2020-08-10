package com.wei.cloud.controller;

import com.wei.base.springframework.constant.vo.RestfulVO;
import com.wei.cloud.vo.UserVO;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    private String test;

    @GetMapping("/test")
    public String test() {
        return test;
    }

    @PostMapping("/test1")
    public String test(@RequestBody UserVO userVO) {
        System.err.println(userVO.toString());
        return "test";
    }

    @PostMapping("/test2/{ttt}")
    public RestfulVO<String> test2(@PathVariable("ttt") String ttt) {
        System.err.println(ttt);
        return new RestfulVO("test");
    }
}