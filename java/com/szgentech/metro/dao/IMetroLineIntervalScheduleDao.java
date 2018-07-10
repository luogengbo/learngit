package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroLineIntervalSchedule;

/**
 * 线路区间进度计划Dao
 * 
 * @author luohao
 *
 */
public interface IMetroLineIntervalScheduleDao extends BaseDao<MetroLineIntervalSchedule> {

	/**
	 * 查找计划进度
	 * 
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalSchedule> findSchedule(Map<String, Object> params);

	/**
	 * 按区间左右线查找左右线每天计划进度数据
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalSchedule> findIntervalLrDailySchedule(Map<String, Object> params);
	
	/**
	 * 按区间左右线查找左右线月计划进度数据
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalSchedule> findIntervalLrMonthlySchedule(Map<String, Object> params);
	
	/**
	 * 批量插入计划进度
	 * 
	 * @param scheduleList
	 * @return
	 */
	int addScheduleBatch(List<MetroLineIntervalSchedule> scheduleList);


}