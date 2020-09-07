package com.wei.base.springframework.cloud.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EagerLoad implements Serializable {

    private static final long serialVersionUID = 6222379748331032436L;

    /**
     * 需要排除的服务
     */
    private Boolean autoEnabled = Boolean.FALSE;

    /**
     * 需要排除的服务
     */
    private List<String> excludes;
}