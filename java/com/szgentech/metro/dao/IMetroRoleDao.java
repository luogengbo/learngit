package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroRole;
/**
 * 角色管理数据处理接口
 * @author MAJL
 *
 */
public interface IMetroRoleDao extends BaseDao<MetroRole> {
	/**
	 * 添加角色菜单关系
	 * @param params
	 * @return
	 */
	int addRoleAndMenuRel(Map<String, Object> params);
	
	/**
	 * 删除角色菜单关系
	 * @param params
	 * @return
	 */
	int delRoleAndMenuRel(Map<String, Object> params);
	
	/**
	 * 查询所有角色 超级管理员除外
	 * @return
	 */
	List<MetroRole> findAllRoleInfo();
}
