package com.wei.base.springframework.util.kaptcha.vo;

import com.wei.base.springframework.util.kaptcha.enums.VerifyImageTypeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class VerifyImageType implements Serializable {

    private static final long serialVersionUID = 7281281906050861759L;
    /**
     * 验证码类型,0:字符串验证码,1:计算验证码,2:滑动验证码
     */
    private Integer type = VerifyImageTypeEnum.CHAR.code;
}