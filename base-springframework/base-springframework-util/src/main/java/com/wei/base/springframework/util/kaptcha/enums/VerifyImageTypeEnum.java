package com.wei.base.springframework.util.kaptcha.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VerifyImageTypeEnum {

    CHAR(1, "字符验证码"), OPERATION(2, "运算验证码"), SLIDE(3, "滑动验证码"),
    TEXT_SELECTION(4, "文字点选验证码"),
    ;

    public Integer code;

    public String msg;

    public static VerifyImageTypeEnum getEnumByCode(Integer code) {
        for (VerifyImageTypeEnum result : VerifyImageTypeEnum.values()) {
            if (result.getCode().equals(code)) {
                return result;
            }
        }

        return null;
    }
}