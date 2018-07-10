package com.szgentech.metro.base.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.szgentech.metro.service.ISysRightService;

/**
 * 系统初始化数据
 * 
 * @author MAJL
 *
 */
public class SysDataInit {
	@Autowired
	private ISysRightService rightService;

	/**
	 * 初始化权限数据到memcached缓存
	 */
	@PostConstruct
	private void init() {
		rightService.setRightMenusByRoleId(1l);
		rightService.setRightMenusByRoleId(null);
		rightService.setRightDatasByUserId(1l);
		rightService.setRightDatasByUserId(null);
	}

}
