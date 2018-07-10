package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroLineIntervalLr;

/**
 * 地铁线路区间左右线信息数据dao
 * @author hank
 *
 * 2016年8月16日
 */
public interface IMetroLineIntervalLrDao extends BaseDao<MetroLineIntervalLr> {
	
	/**
	 * 查找线路区间的左右线信息数据列表
	 * @param intervalId
	 * @return
	 */
	List<MetroLineIntervalLr> findObjsForLineInterval(Long intervalId);

	MetroLineIntervalLr findIntervalLr(Map<String, Object> params);
}
