package com.szgentech.metro.service;

import java.util.List;

import com.szgentech.metro.model.MetroSysMenu;

/**
 * 菜单管理业务处理接口
 * @author MAJL
 *
 */
public interface IMetroSysMenuService {
	/**
	 * 查询所有菜单
	 * @param level 菜单级别 返回1到level的菜单
	 * @return
	 */
	List<MetroSysMenu> findMenuAll(int level);
	
}
