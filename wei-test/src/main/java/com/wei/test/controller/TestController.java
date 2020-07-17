package com.wei.test.controller;

import com.wei.base.springframework.core.vo.RestfulVO;
import com.wei.test.vo.UserVO;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    private String test = "123456";

    @GetMapping("/test")
    public RestfulVO<String> test(@RequestParam("test") String test1) {
        System.err.println(test1);
        return new RestfulVO("test");
    }

    @PostMapping("/test1")
    public RestfulVO<String> test(@RequestBody UserVO userVO) {
        System.err.println(userVO.toString());
        return new RestfulVO("test");
    }

    @PostMapping("/test2/{ttt}")
    public RestfulVO<String> test2(@PathVariable("ttt") String ttt) {
        System.err.println(ttt);
        return new RestfulVO("test");
    }
}