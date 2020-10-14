package com.wei.developer.platform.vo.respon;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyImageResp implements Serializable {

    private static final long serialVersionUID = 3677284647136720202L;

    @ApiModelProperty("验证码ID")
    private Long id;

    @ApiModelProperty("base64验证码图片")
    private String image;

    @ApiModelProperty("点选文字提示")
    private String tips;

    @ApiModelProperty("滑动验证码，裁剪图")
    private String cutoutImage;

    @ApiModelProperty("滑动验证码，Y轴")
    private Integer y;
}