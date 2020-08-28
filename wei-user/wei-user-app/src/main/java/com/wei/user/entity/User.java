package com.wei.user.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author wei
 * @since 2020-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户手机号
     */
    private String mobilePhone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户头像地址
     */
    private String pictureUrl;

    /**
     * 创建时间
     */
    private Date createTime;


}
