package com.wei.base.springframework.core.web.exception;

/**
 * 公共异常类
 *
 * @author : weierming
 * @date : 2020/7/10
 */
public interface BaseException {

    Integer getCode();

    String getMsg();
}
