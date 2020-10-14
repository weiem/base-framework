package com.wei.developer.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wei
 * @since 2020-10-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
     * 是否是管理员,0:否,1:是
     */
    private Integer isAdmin;

    /**
     * 是否启用,0:禁用,1:启用
     */
    private Integer isEnable;

    /**
     * 删除标识,0:未删除,1:已删除
     */
    private Integer isDelete;

    private Long createId;

    private LocalDateTime createTime;

    private Long updateId;

    private LocalDateTime updateTime;


}
