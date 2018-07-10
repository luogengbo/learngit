package com.szgentech.metro.service;

import java.util.List;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroRole;

/**
 * 角色管理业务接口
 * @author MAJL
 *
 */
public interface IMetroRoleService {
	/**
	 * 分页查询角色
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroRole> findRoleList(int pageNum, int pageSize);
	
	/**
	 * 添加角色
	 * @param roleCode 角色代码
	 * @param roleName 角色名称
	 * @param isUsed 是否启用
	 * @param roleDescribe 描述
	 * @param menuIds 角色菜单Id多个用逗号分隔
	 * @return
	 */
	boolean addRole(String roleCode, String roleName,String isUsed, String roleDescribe, String menuIds);
	
	/**
	 * 修改角色信息
	 * @param roleId 角色Id
	 * @param roleCode 角色代码
	 * @param roleName 角色名称
	 * @param isUsed 是否启用
	 * @param roleDescribe 描述
	 * @param oldMenuIds 修改前角色菜单Id多个用逗号分隔
	 * @param menuIds 角色菜单Id多个用逗号分隔
	 * @return
	 */
	boolean editRole(String roleId, String roleCode, String roleName,String isUsed, String roleDescribe,String oldMenuIds, String menuIds);
	
	/**
	 * 删除角色
	 * @param roleId 角色Id
	 * @return
	 */
	boolean delRole(String roleId);
	
	/**
	 * 查找角色信息包含角色菜单权限
	 * @param roleId 角色Id
	 * @return
	 */
	MetroRole findRoleById(String roleId);
	
	/**
	 * 查询所有角色 超级管理员除外
	 * @return
	 */
	List<MetroRole> findAllRoleInfo();
}
