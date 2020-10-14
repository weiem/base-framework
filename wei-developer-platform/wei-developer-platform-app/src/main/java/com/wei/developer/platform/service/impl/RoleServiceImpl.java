package com.wei.developer.platform.service.impl;

import com.wei.developer.platform.entity.Role;
import com.wei.developer.platform.mapper.RoleMapper;
import com.wei.developer.platform.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author wei
 * @since 2020-10-10
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
}
