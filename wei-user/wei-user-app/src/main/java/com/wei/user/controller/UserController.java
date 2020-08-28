package com.wei.user.controller;

import com.wei.base.springframework.constant.vo.RestfulVO;
import com.wei.user.entity.User;
import com.wei.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("分页查询所有数据")
    @GetMapping("/test")
    public User test() {
        return userService.getById(1);
    }

    @ApiOperation("分页查询所有数据")
    @GetMapping("/test2")
    public RestfulVO<User> test2() {
        return new RestfulVO(userService.getById(1));
    }

    @ApiOperation("分页查询所有数据")
    @GetMapping("/test1")
    public RestfulVO<String> test1() {
        return new RestfulVO("1111");
    }
}
