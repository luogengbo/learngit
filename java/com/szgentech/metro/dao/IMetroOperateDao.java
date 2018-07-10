package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroUserOperateRec;

/**
 * 用户操作记录管理接口
 * @author MAJL
 *
 */
public interface IMetroOperateDao extends BaseDao<MetroUserOperateRec> {

	int insertObjs(Map<String, Object> params);
	
	List<MetroUserOperateRec> usernameList(Map<String, Object> params);
	
	List<MetroUserOperateRec> visitMenuList(Map<String, Object> params);
	
	int visitMenu(Map<String, Object> params);
	
	List<MetroUserOperateRec> operationList(Map<String, Object> params);
	
	int operation(Map<String, Object> params);
}
