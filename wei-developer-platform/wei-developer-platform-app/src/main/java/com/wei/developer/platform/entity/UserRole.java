package com.wei.developer.platform.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户角色关系表
 * </p>
 *
 * @author wei
 * @since 2020-10-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 删除标识,0:未删除,1:已删除
     */
    private Integer isDelete;

    private Long createId;

    private LocalDateTime createTime;

    private Long updateId;

    private LocalDateTime updateTime;


}
