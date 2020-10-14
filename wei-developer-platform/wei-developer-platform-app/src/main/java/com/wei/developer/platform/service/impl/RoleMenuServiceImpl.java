package com.wei.developer.platform.service.impl;

import com.wei.developer.platform.entity.RoleMenu;
import com.wei.developer.platform.mapper.RoleMenuMapper;
import com.wei.developer.platform.service.RoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色菜单关系表 服务实现类
 * </p>
 *
 * @author wei
 * @since 2020-10-10
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

	@Autowired
	private RoleMenuMapper roleMenuMapper;
}
