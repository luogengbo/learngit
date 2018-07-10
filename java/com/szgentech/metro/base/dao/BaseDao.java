package com.szgentech.metro.base.dao;

import java.util.List;
import java.util.Map;

/**
 * 基础数据操作接口
 * 
 * @author MAJL
 *
 * @param <T>
 */
public interface BaseDao<T> {
	/**
	 * 添加
	 * 
	 * @param obj
	 * @return
	 */
	int insertObj(T obj);

	/**
	 * 修改
	 * 
	 * @param params
	 * @return
	 */
	int updateObj(Map<String, Object> params);

	/**
	 * 删除
	 * 
	 * @param params
	 * @return
	 */
	int deleteObj(Map<String, Object> params);

	/**
	 * 通过id查找
	 * 
	 * @param params
	 * @return
	 */
	T findObjById(Map<String, Object> params);

	/**
	 * 统计
	 * 
	 * @param params
	 * @return
	 */
	int countObjs(Map<String, Object> params);

	/**
	 * 查找数据列表
	 * 
	 * @param params
	 * @return
	 */
	List<T> findObjsList(Map<String, Object> params);
}
