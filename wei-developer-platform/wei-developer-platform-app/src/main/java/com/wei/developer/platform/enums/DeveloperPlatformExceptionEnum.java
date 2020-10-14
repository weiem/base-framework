package com.wei.developer.platform.enums;

import com.wei.base.springframework.constant.exception.BaseException;
import lombok.Getter;

@Getter
public enum DeveloperPlatformExceptionEnum implements BaseException {

    COMMENT_SERVICE_EXCEPTION(1000, "%s"),
    VERIFY_IMAGE_TYPE_ERROR(1001, "验证码类型错误!"),
    GENERATE_VERIFY_IMAGE_ERROR(1002, "生成验证码错误!"),
    VERIFY_IS_INVALID(1003, "验证码已失效!"),
    VERIFY_ERROR(1004, "验证码错误!"),
    ;

    private Integer code;
    private String msg;

    DeveloperPlatformExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}