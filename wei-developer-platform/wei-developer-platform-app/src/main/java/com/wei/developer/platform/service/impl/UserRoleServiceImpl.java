package com.wei.developer.platform.service.impl;

import com.wei.developer.platform.entity.UserRole;
import com.wei.developer.platform.mapper.UserRoleMapper;
import com.wei.developer.platform.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author wei
 * @since 2020-10-10
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

	@Autowired
	private UserRoleMapper userRoleMapper;
}
