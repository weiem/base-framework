package com.wei.base.springframework.constant.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分页入参
 *
 * @author : weierming
 * @date : 2020/9/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest implements Serializable {

    private static final long serialVersionUID = 2270218228565313137L;

    /**
     * 每页显示条数，默认 10
     */
    protected Integer size = 10;

    /**
     * 当前页
     */
    protected Integer current = 1;
}