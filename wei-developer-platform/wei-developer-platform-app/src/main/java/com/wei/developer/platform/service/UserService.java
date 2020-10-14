package com.wei.developer.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.developer.platform.entity.User;
import com.wei.developer.platform.vo.request.CheckVerifyImageRequest;
import com.wei.developer.platform.vo.respon.VerifyImageResp;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wei
 * @since 2020-10-10
 */
public interface UserService extends IService<User> {

    /**
     * 生成验证码图片
     *
     * @param verifyImageType 验证码类型
     * @return 验证码
     */
    VerifyImageResp generateVerifyImage(Integer verifyImageType);

    /**
     * 校验验证码
     *
     * @param request 验证码参数
     */
    void checkVerifyImage(CheckVerifyImageRequest request);
}