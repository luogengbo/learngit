package com.szgentech.metro.dao;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroLineIntervalPp;

/**
 * 地铁线路区间工程进度dao
 * @author hank
 *
 * 2016年8月16日
 */
public interface IMetroLineIntervalPpDao extends BaseDao<MetroLineIntervalPp> {

	/**
	 * 通过intervalId查询model
	 * @param intervalId
	 * @return
	 */
	MetroLineIntervalPp findByIntervalId(Long intervalId);
	

}
