package com.wei.base.springframework.util.kaptcha.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlideVerifyImage implements Serializable {

    private static final long serialVersionUID = -6164216580154526973L;

    /**
     * 滑动验证码
     */
    private String image;

    /**
     * 滑动验证码，裁剪图
     */
    private String cutoutImage;

    /**
     * 滑动验证码，x轴
     */
    private Integer x;

    /**
     * 滑动验证码，Y轴
     */
    private Integer y;
}