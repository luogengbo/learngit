package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroLineIntervalRingReport;
import com.szgentech.metro.model.MonitorInfoCity;
import com.szgentech.metro.model.RingStatistics;

/**
 * 环报表Dao
 * 
 * @author luohao
 *
 */
public interface IMetroLineIntervalRingReportDao extends BaseDao<MetroLineIntervalRingReport> {

	
	/**
	 * 查找
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalRingReport> findRingReport(Map<String, Object> params);
	
	/**
	 * 查询环号对应参数的统计值（平均、最大、最小、总和）
	 * @param params
	 * @return
	 */
	List<RingStatistics> findRingStatistics(Map<String, Object> params);

	/**
	 * 查找
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalRingReport> findRingTowParamReduce(Map<String, Object> params);
	
	/**
	 * 批量插入
	 * 
	 * @param ringReportList
	 * @return
	 */
	int addRingReportBatch(List<MetroLineIntervalRingReport> ringReportList);

	/**
	 * 批量删除
	 * 
	 * @param idList id列表
	 * @return
	 */
	int deleteRingReportBatch(List<String> idList);
	
	/**
	 * 区间左右线信息
	 * @param params
	 * @return
	 */
	MonitorInfoCity findMonitorInfoCity(Map<String, Object> params);


}