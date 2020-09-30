package com.wei.user.mapstruct;

import com.wei.user.entity.User;
import com.wei.user.excelEntity.UserExcel;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 用户信息表 服务类型实体转换
 *
 * @author wei
 * @since 2020-08-13
 */
@Mapper(componentModel = "spring")
public interface UserMapstruct {

    List<UserExcel> userToUserExcel(List<User> user);
}