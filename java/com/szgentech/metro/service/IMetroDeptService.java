package com.szgentech.metro.service;

import java.util.List;

import com.szgentech.metro.model.MetroDept;

/**
 * 部门业务服务接口
 * @author MAJL
 *
 */
public interface IMetroDeptService {
	
	/**
	 * 查询所有部门
	 * @return
	 */
	List<MetroDept> findAllDeptInfo();
	
	/**
	 * 添加部门
	 * @param deptName 部门名称
	 * @return
	 */
	boolean addMetroDeptInfo(String deptName);
	
	/**
	 * 修改部门名称
	 * @param deptId 部门系统Id
	 * @param deptName 部门新名称
	 * @return
	 */
	boolean editMetroDeptInfo(String deptId, String deptName);
	
	/**
	 * 删除部门和删除没有部门的用户
	 * @param deptId
	 * @return
	 */
	boolean delMetroDeptInfo(String deptId);
	
}
