package com.szgentech.metro.dao;

import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroLineIntervalMdRe;

/**
 * 地铁线路区间上传监测数据记录dao
 * @author hank
 *
 * 2016年8月16日
 */
public interface IMetroLineIntervalMdReDao extends BaseDao<MetroLineIntervalMdRe> {
	
	/**
	 * 按日期查询当天的最新监测日期
	 * @param params  监测日期
	 * @return
	 */
	Integer findDayTheLatestMonitorDate(Map<String, Object> params);
	
	

}
