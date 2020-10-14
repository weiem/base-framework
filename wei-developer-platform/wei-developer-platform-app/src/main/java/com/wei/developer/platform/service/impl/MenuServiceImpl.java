package com.wei.developer.platform.service.impl;

import com.wei.developer.platform.entity.Menu;
import com.wei.developer.platform.mapper.MenuMapper;
import com.wei.developer.platform.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author wei
 * @since 2020-10-10
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

	@Autowired
	private MenuMapper menuMapper;
}
