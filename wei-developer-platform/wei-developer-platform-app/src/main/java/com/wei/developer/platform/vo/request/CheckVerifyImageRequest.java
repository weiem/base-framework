package com.wei.developer.platform.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckVerifyImageRequest implements Serializable {

    private static final long serialVersionUID = -2617871777362224762L;

    @NotNull(message = "验证码ID不能为空!")
    @ApiModelProperty(value = "验证码ID", required = true)
    private Long id;

    @NotNull(message = "验证码类型不能为空!")
    @ApiModelProperty(value = "验证码类型,验证码类型,1:字符验证码,2:运算验证码,3:滑动验证码", required = true)
    private Integer type;

    @NotEmpty(message = "验证码值不能为空!")
    @ApiModelProperty(value = "验证码值", required = true)
    private String value;

    @ApiModelProperty(value = "滑动验证码，Y轴")
    private String y;
}