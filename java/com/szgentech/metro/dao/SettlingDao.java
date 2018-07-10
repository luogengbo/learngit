package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.Homepage;
import com.szgentech.metro.model.MetroLineInterval;
import com.szgentech.metro.model.MetroUpdateApp;
import com.szgentech.metro.model.SettlingMd;
import com.szgentech.metro.model.SettlingName;
import com.szgentech.metro.model.SettlingSp;
import com.szgentech.metro.model.SettlingTotal;
import com.szgentech.metro.model.SettlingVelocity;


public interface SettlingDao extends BaseDao<MetroUpdateApp>{

	/**
	 * APP查询沉降速率
	 * @param params
	 * @return
	 */
	SettlingVelocity getSettlingVelocity(Map<String, Object> params);
	
	/**
	 * APP累计沉降
	 * @param params
	 * @return
	 */
	SettlingTotal getSettlingTotal(Map<String, Object> params);
	
	/**
	 * APP沉降点（一次查询返回所有数据）
	 * @param params
	 */
	List<Homepage> findHomepage(Map<String, Object> params);
	
	/**
	 * APP沉降点名称查询
	 * @param params
	 * @return
	 */
	List<SettlingName> findSettlingName(Map<String, Object> params);
	
	/**
	 * 沉降速率详情
	 * @param params
	 * @return
	 */
	List<SettlingSp> findSettlingSp(Map<String, Object> params);
	
	/**
	 * 累计沉降详情
	 * @param params
	 * @return
	 */
	List<SettlingMd> findSettlingMd(Map<String, Object> params);
	
	/**
	 * APP管片(查询线路区间的管片ID)
	 * @param params
	 * @return
	 */
	MetroLineInterval findDuctId(Map<String, Object> params);

	/**
	 * APP(检查版本更新)
	 * @return
	 */
	MetroUpdateApp MetroUpdateData(Map<String, Object> params);
	
	/**
	 * APP历史上传记录
	 * @return
	 */
	List<MetroUpdateApp> ListMetroUpdateData(Map<String, Object> params);
	
	/**
	 * 查询记录的行数和页数
	 * @param params
	 * @return
	 */
	int countMetroUpdateData(Map<String, Object> params);
	
	/**
	 * 查找该版本是否存在
	 */
	int findMetroUpdateAppC(Map<String, Object> params);
}
