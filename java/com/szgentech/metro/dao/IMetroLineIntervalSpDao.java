package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroLineIntervalSp;

/**
 * 地铁线路区间沉降点dao
 * @author hank
 *
 * 2016年8月16日
 */
public interface IMetroLineIntervalSpDao extends BaseDao<MetroLineIntervalSp> {

	/**
	 * 查询指定区间乘降点信息
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalSp> findLineIntervalSps(Map<String, Object> params);

	/**
	 * 
	 * @param params
	 * @return
	 */
	MetroLineIntervalSp findUniqueData(Map<String, Object> params);

}
