package com.szgentech.metro.dao;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroUserDataRel;
/**
 * 数据权限管理数据处理接口
 * @author MAJL
 *
 */
public interface IMetroDataRightDao extends BaseDao<MetroUserDataRel> {
	
	/**
	 * 批量添加用户数据权限关系
	 * @param params
	 * @return
	 */
	int insertObjs(Map<String, Object> params);
	
	/**
	 * 查询用户的所有数据权限关系列表
	 * @param params
	 * @return
	 *//*
	List<MetroUserDataRel> findObjsByUserId(Map<String, Object> params);*/

}
