package com.wei.base.springframework.util.kaptcha.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatorVerifyImage implements Serializable {

    private static final long serialVersionUID = 5296643113584217325L;

    /**
     * 运算符验证码
     */
    private String image;

    /**
     * 计算结果
     */
    private String value;
}