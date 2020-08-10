package com.wei.base.springframework.cloud.web.handler;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.wei.base.springframework.constant.enums.RestfulEnum;
import com.wei.base.springframework.constant.vo.RestfulVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 公共异常处理类
 *
 * @author admin
 */
@Slf4j
@ControllerAdvice
public class BaseExceptionHandler {

    @Value("${debug:false}")
    private boolean debug;

    @ExceptionHandler(HystrixRuntimeException.class)
    @ResponseBody
    public RestfulVO<String> hystrixRuntimeException(HystrixRuntimeException e) {
        log.error(e.getMessage(), e.getCause());
        return new RestfulVO<>(RestfulEnum.FAIL, "系统异常!");
    }

    /**
     * 封装javax.validation抛出的错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public RestfulVO<String> constraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuilder stringBuilder = new StringBuilder();

        constraintViolations.forEach(vo -> {
            stringBuilder.append(vo.getMessage() + ";");
        });

        return new RestfulVO<>(RestfulEnum.FAIL, stringBuilder.toString());
    }
}