package com.wei.base.springframework.util.enums;

import com.wei.base.springframework.constant.exception.BaseException;
import lombok.Getter;

@Getter
public enum UtilExceptionEnum implements BaseException {

    COMMENT_SERVICE_EXCEPTION(1000, "%s"),
    GENERATE_VERIFY_IMAGE_ERROR(1001, "生成验证码错误!"),
    NO_VERIFY_IMAGE(1002, "没有验证码图片!"),
    ;

    private Integer code;
    private String msg;

    UtilExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}