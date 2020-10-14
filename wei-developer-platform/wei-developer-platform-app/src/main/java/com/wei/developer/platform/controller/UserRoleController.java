package com.wei.developer.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wei.developer.platform.service.UserRoleService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户角色关系表 前端控制器
 * </p>
 *
 * @author wei
 * @since 2020-10-10
 */
@RestController
@RequestMapping("/userRole")
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleService;
}
