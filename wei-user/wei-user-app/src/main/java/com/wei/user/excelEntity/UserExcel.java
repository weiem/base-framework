package com.wei.user.excelEntity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class UserExcel implements Serializable {


    private static final long serialVersionUID = 1072879095890203450L;

    @ExcelProperty(value = "id", index = 0)
    private Long id;

    /**
     * 用户名称
     */
    @ExcelProperty(value = "用户名称", index = 1)
    private String name;

    /**
     * 用户手机号
     */
    @ExcelProperty(value = "用户手机号", index = 2)
    private String mobilePhone;

    /**
     * 用户邮箱
     */
    @ExcelProperty(value = "用户邮箱", index = 3)
    private String email;

    /**
     * 密码
     */
    @ExcelProperty(value = "密码", index = 4)
    private String password;

    /**
     * 用户头像地址
     */
    @ExcelProperty(value = "用户头像地址", index = 5)
    private String pictureUrl;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 6)
    private Date createTime;

    List<UserExcel> user;

    List<String> list;
}
