package com.wei.user.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.wei.base.springframework.constant.vo.RestfulVO;
import com.wei.base.springframework.easyexcel.controller.BaseExport;
import com.wei.user.entity.User;
import com.wei.user.excelEntity.UserExcel;
import com.wei.user.mapstruct.UserMapstruct;
import com.wei.user.service.UserService;
import com.wei.user.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
public class UserController implements BaseExport<UserVO, UserExcel> {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapstruct userMapstruct;

    @ApiOperation("分页查询所有数据")
    @GetMapping("/test")
    public User test(User user) {
        return userService.getById(1);
    }

    @ApiOperation("分页查询所有数据")
    @GetMapping("/test2")
    public boolean test2(@RequestParam(value = "test") String test) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(User::getEmail, "11111");
        return userService.update(updateWrapper);
    }

    @ApiOperation("分页查询所有数据")
    @GetMapping("/test1")
    public RestfulVO<String> test1() {
        return new RestfulVO("1111");
    }

    @ApiOperation("分页查询所有数据")
    @PostMapping("/test3")
    public void test1(@RequestBody UserVO user) {
        System.err.println(11111);
        return;
    }

    @ApiOperation("分页查询所有数据")
    @PutMapping("/put")
    public void put(@RequestBody UserVO user) {
        System.err.println(11111);
        return;
    }

    @ApiOperation("分页查询所有数据")
    @GetMapping("/test4")
    public void test1(Object object) throws JsonProcessingException {
        return;
    }

    @ApiOperation("批量插入")
    @PostMapping("/insertBatchSomeColumn")
    public Integer insertBatchSomeColumn(@RequestBody List<User> list) {
        return userService.insertBatchSomeColumn(list);
    }

    @ApiOperation("查询")
    @PostMapping("/getById")
    public User getById(@RequestBody List<User> list) {
        return userService.getById(1);
    }

    @ApiOperation("查询")
    @PostMapping("/getPage")
    public Page<User> getPage(UserVO userVO) {
        Page page = new Page(userVO.getCurrent(), userVO.getSize());
        return userService.page(page);
    }

    @Override
    public Page<?> getExportData(UserVO userVO) {
        return getPage(userVO);
    }
}
