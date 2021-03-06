package com.wei.developer.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author wei
 * @since 2020-10-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 是否默认角色,0:否,1:是
     */
    private Integer isDefault;

    /**
     * 是否启用,0:禁用,1:启用
     */
    private Integer isEnable;

    private Long createId;

    private LocalDateTime createTime;

    private Long updateId;

    private LocalDateTime updateTime;


}
