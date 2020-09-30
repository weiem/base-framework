package com.wei.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.user.entity.User;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author wei
 * @since 2020-08-13
 */
public interface UserService extends IService<User> {

    Integer insertBatchSomeColumn(List<User> list);
}
