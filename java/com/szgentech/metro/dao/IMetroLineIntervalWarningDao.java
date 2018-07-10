package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.background.vo.IntervalWarningVO;
import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroLineIntervalWarning;

/**
 * 地铁线路区间监测预警阈值dao
 * @author hank
 *
 * 2016年8月16日
 */
public interface IMetroLineIntervalWarningDao extends BaseDao<MetroLineIntervalWarning> {
	/**
	 * 获取某线路区间左或右线的监测预警阀值设置信息列表
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalWarning> findAllByIntervalIdAndLr(Map<String, Object> params);
	
	/**
	 * 通过唯一性条件查询唯一数据
	 * @param params
	 * @return
	 */
	MetroLineIntervalWarning findUniqueData(Map<String, Object> params);
	/**
	 * 获取所有监测预警阀值设置信息的路线左右线列表，用于historian接口查询该路线对应左右线的环数
	 * @return
	 */
	List<IntervalWarningVO> findQueryVOListForAll();
	
	/**
	 * 获取某线路左右线的检测预警阈值设置信息列表，用于historian接口查询符合环数要求的数据
	 * @param params
	 * @return
	 */
	List<IntervalWarningVO> findQueryVOListForParams(Map<String, Object> params);

	/**
	 * 获取某线路左右线的检测预警阈值设置信息列表，用于historian接口查询符合环数要求的数据
	 * @param params
	 * @return
	 */
	List<IntervalWarningVO> findQueryVOList(Map<String, Object> params);
	

}
