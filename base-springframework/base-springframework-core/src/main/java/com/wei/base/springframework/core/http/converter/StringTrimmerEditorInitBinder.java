package com.wei.base.springframework.core.http.converter;


import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 去除get入参前后空格
 *
 * @author : weierming
 * @date : 2020/10/13
 */
@ControllerAdvice
public class StringTrimmerEditorInitBinder {

    /**
     * 去除get方式的参数空格
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 创建 String trim 编辑器
        // 构造方法中 boolean 参数含义为如果是空白字符串,是否转换为null
        // 即如果为true,那么 " " 会被转换为 null,否者为 ""
        StringTrimmerEditor propertyEditor = new StringTrimmerEditor(true);
        // 为 String 类对象注册编辑器
        binder.registerCustomEditor(String.class, propertyEditor);
    }
}