package com.wei.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wei.user.entity.User;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author wei
 * @since 2020-08-13
 */
public interface UserMapper extends BaseMapper<User> {

    int insertBatchSomeColumn(List<User> entityList);
}
