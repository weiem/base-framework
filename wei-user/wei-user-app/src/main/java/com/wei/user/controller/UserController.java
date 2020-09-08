package com.wei.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wei.base.springframework.constant.vo.RestfulVO;
import com.wei.base.springframework.redis.utils.RedisLock;
import com.wei.user.entity.User;
import com.wei.user.service.UserService;
import com.wei.user.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author wei
 * @since 2020-08-13
 */
@Api(tags = "test")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisLock redisLock;

    @ApiOperation("分页查询所有数据")
    @GetMapping("/test")
    public User test(User user) {
        return userService.getById(1);
    }

    @ApiOperation("分页查询所有数据")
    @GetMapping("/test2")
    public RestfulVO<User> test2(@RequestParam(value = "test") String test) {
        return new RestfulVO(userService.getById(1));
    }

    @ApiOperation("分页查询所有数据")
    @GetMapping("/test1")
    public RestfulVO<String> test1() {
        return new RestfulVO("1111");
    }

    @ApiOperation("分页查询所有数据")
    @PostMapping("/test3")
    public void test1(@RequestBody UserVO user) {
        System.err.println(redisLock.lock("test"));
        System.err.println(11111);
        System.err.println(redisLock.unlock("test"));
        return;
    }

    @ApiOperation("分页查询所有数据")
    @GetMapping("/test4")
    public void test1(Object object) throws JsonProcessingException {
        System.err.println(object.toString());
        return;
    }
}
