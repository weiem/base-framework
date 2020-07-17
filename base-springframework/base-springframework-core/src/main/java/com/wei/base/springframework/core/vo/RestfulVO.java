package com.wei.base.springframework.core.vo;

import com.wei.base.springframework.core.enums.RestfulEnum;
import com.wei.base.springframework.core.web.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一返回值
 *
 * @author : weierming
 * @date : 2020/7/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestfulVO<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5551289026614581502L;

    private Integer code;

    private String message;

    private T data;

    public RestfulVO(T data) {
        code = RestfulEnum.SUCCESS.code;
        message = RestfulEnum.SUCCESS.msg;
        this.data = data;
    }

    public RestfulVO<T> fail(T data) {
        code = RestfulEnum.FAIL.code;
        message = RestfulEnum.FAIL.msg;
        this.data = data;

        return this;
    }

    public RestfulVO(ServiceException e) {
        code = e.getCode();
        message = e.getMessage();
    }

    public RestfulVO(ServiceException e, String msg) {
        code = e.getCode();
        message = msg;
    }

    public RestfulVO(RestfulEnum restfulEnum, String msg) {
        code = restfulEnum.getCode();
        message = msg;
    }
}