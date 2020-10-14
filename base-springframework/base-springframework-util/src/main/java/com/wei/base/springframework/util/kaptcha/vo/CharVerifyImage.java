package com.wei.base.springframework.util.kaptcha.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharVerifyImage implements Serializable {

    private static final long serialVersionUID = 5296643113584217325L;

    /**
     * 字符验证码
     */
    private String image;

    /**
     * 验证码图片内容
     */
    private String value;
}
