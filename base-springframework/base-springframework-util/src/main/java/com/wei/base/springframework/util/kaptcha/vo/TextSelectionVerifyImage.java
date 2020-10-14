package com.wei.base.springframework.util.kaptcha.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextSelectionVerifyImage implements Serializable {

    private static final long serialVersionUID = -6164216580154526973L;

    /**
     * 滑动验证码
     */
    private String image;

    /**
     * 点选文字提示
     */
    private String tips;

    /**
     * 文字坐标顺序
     */
    private List<String> codeAxis;
}