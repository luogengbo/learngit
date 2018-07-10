package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.RingStatus;

/**
 * 环状态dao
 * 
 * @author luohao
 *
 *         2018年4月24日
 */
public interface IRingStatusDao extends BaseDao<RingStatus> {

	List<RingStatus> findRingTimeInfo(Map<String, Object> params);
	List<RingStatus> findRingNumInfo(Map<String, Object> params);

}
