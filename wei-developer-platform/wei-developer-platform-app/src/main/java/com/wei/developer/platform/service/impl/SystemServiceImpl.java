package com.wei.developer.platform.service.impl;

import com.wei.developer.platform.entity.System;
import com.wei.developer.platform.mapper.SystemMapper;
import com.wei.developer.platform.service.SystemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统表 服务实现类
 * </p>
 *
 * @author wei
 * @since 2020-10-10
 */
@Service
public class SystemServiceImpl extends ServiceImpl<SystemMapper, System> implements SystemService {

	@Autowired
	private SystemMapper systemMapper;
}
