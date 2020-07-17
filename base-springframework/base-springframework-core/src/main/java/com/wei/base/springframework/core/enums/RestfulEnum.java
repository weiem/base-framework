package com.wei.base.springframework.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestfulEnum {

    SUCCESS(0, "success"), FAIL(9999, "fail");

    public Integer code;

    public String msg;
}
