package com.wei.developer.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wei.developer.platform.service.SystemService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统表 前端控制器
 * </p>
 *
 * @author wei
 * @since 2020-10-10
 */
@RestController
@RequestMapping("/system")
public class SystemController {

	@Autowired
	private SystemService systemService;
}
