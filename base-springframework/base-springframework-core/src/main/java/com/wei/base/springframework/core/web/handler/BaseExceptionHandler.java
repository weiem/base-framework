package com.wei.base.springframework.core.web.handler;

import com.wei.base.springframework.core.enums.RestfulEnum;
import com.wei.base.springframework.core.vo.RestfulVO;
import com.wei.base.springframework.core.web.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    /**
     * 拦截ServletException类的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public RestfulVO<String> servletException(ServiceException e) {
        log.error(e.getMessage(), e);
        return new RestfulVO<>(e);
    }

    /**
     * 空指针异常要做单独封装 不然返回null时data会被过滤掉
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public RestfulVO<String> servletException(NullPointerException e) {
        log.error(e.getMessage(), e);
        return new RestfulVO<>(RestfulEnum.FAIL, "系统错误!");
    }

//    @ExceptionHandler(HystrixRuntimeException.class)
//    @ResponseBody
//    public RestfulVO<String> hystrixRuntimeException(HystrixRuntimeException e) {
//        log.error(e.getMessage(), e.getCause());
//        return new RestfulVO<>(RestfulEnum.FAIL, "系统错误!");
//    }

    /**
     * 封装javax.validation.Valid抛出的错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public RestfulVO<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        StringBuilder stringBuilder = new StringBuilder();

        allErrors.forEach(vo -> {
            stringBuilder.append(vo.getDefaultMessage().toString() + ";");
        });
        return new RestfulVO<>(RestfulEnum.FAIL, stringBuilder.toString());
    }

    /**
     * 封装javax.validation抛出的错误
     *
     * @param e
     * @return
     */
//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseBody
//    public RestfulVO<String> constraintViolationException(ConstraintViolationException e) {
//        log.error(e.getMessage(), e);
//        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
//        StringBuilder stringBuilder = new StringBuilder();
//
//        constraintViolations.forEach(vo -> {
//            stringBuilder.append(vo.getMessage() + ";");
//        });
//
//        return new RestfulVO<>(RestfulEnum.FAIL, stringBuilder.toString());
//    }

    /**
     * 封装javax.validation抛出的错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public RestfulVO<String> missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);

        return new RestfulVO<>(RestfulEnum.FAIL, String.format("请求参数%s不存在!", e.getParameterName()));
    }

    /**
     * 封装javax.validation抛出的错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public RestfulVO<String> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);

        return new RestfulVO<>(RestfulEnum.FAIL, "请求参数错误!");
    }

    /**
     * 将错误全部拦截封装放回 如果有其他异常需要自己处理的需要在定义错误拦截器
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestfulVO<String> exception(Exception e) {
        log.error(e.getMessage(), e);
        String msg = debug ? e.getMessage() : "系统错误";
        return new RestfulVO<>(RestfulEnum.FAIL, msg);
    }
}