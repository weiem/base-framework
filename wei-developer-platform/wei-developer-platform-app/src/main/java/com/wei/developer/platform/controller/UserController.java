package com.wei.developer.platform.controller;

import com.wei.base.springframework.util.VerifyImageUtil;
import com.wei.base.springframework.util.kaptcha.vo.CharVerifyImage;
import com.wei.base.springframework.util.kaptcha.vo.OperatorVerifyImage;
import com.wei.base.springframework.util.kaptcha.vo.SlideVerifyImage;
import com.wei.base.springframework.util.kaptcha.vo.TextSelectionVerifyImage;
import com.wei.developer.platform.service.UserService;
import com.wei.developer.platform.vo.request.CheckVerifyImageRequest;
import com.wei.developer.platform.vo.respon.VerifyImageResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wei
 * @since 2020-10-10
 */
@Api(tags = "用户表")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("生成验证码图片")
    @GetMapping("generateVerifyImage")
    public VerifyImageResp generateVerifyImage(@Parameter(description = "验证码类型,1:字符验证码,2:运算验证码,3:滑动验证码,4:文字点选验证码,不传默认1")
                                               @RequestParam(value = "verifyImageType", required = false, defaultValue = "1")
                                                       Integer verifyImageType) {
        return userService.generateVerifyImage(verifyImageType);
    }

    @ApiOperation("校验验证码")
    @GetMapping("checkVerifyImage")
    public void checkVerifyImage(@Valid CheckVerifyImageRequest request) {
        userService.checkVerifyImage(request);
    }

    @ApiOperation("分页查询所有数据")
    @PostMapping("test")
    public CharVerifyImage getCharVerifyImage(@RequestBody CharVerifyImage verifyImage) throws IOException {
        return VerifyImageUtil.getCharVerifyImage();
    }

    @ApiOperation("分页查询所有数据1")
    @GetMapping("test1")
    public OperatorVerifyImage test1() throws IOException {
        return VerifyImageUtil.getOperatorVerifyImage();
    }

    @ApiOperation("分页查询所有数据1")
    @GetMapping("test2")
    public SlideVerifyImage test2() throws IOException {
        return VerifyImageUtil.getSlideVerifyImage();
    }

    @ApiOperation("分页查询所有数据1")
    @GetMapping("test3")
    public TextSelectionVerifyImage test3() throws IOException {
        return VerifyImageUtil.getTextSelectionVerifyImage();
    }
}
