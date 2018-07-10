package com.szgentech.metro.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.model.CommonResponse;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroLineIntervalSchedule;

/**
 * 线路区间计划进度业务接口
 * 
 * @author luohao
 *
 */
public interface IMetroLineIntervalScheduleService {

	/**
	 * 删除区间计划进度
	 * 
	 * @param scheduleId
	 * @return
	 */
	boolean deleteObj(Long scheduleId);

	/**
	 * 分页查询 区间左右计划进度数据
	 * 
	 * @param intervalId
	 *            线路区间id
	 * @param leftOrRight
	 *            左右线
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            单页记录数
	 * @return
	 */
	PageResultSet<MetroLineIntervalSchedule> findPageScheduleInfo(Long intervalId, String leftOrRight,String startDateStr,String endDateStr, int pageNum,
			int pageSize);
	
	/**
	 * 按区间左右线查找左右线计划进度数据
	 * 
	 * @param intervalId   线路区间id
	 * @param leftOrRight  左右线
	 * @param startDateStr 开始日期
	 * @param endDateStr   结束日期
	 * @param cycleType    周期类型
	 * @isFill             未设置计划进度是否填充空计划（计划进度0环）对象
	 * @return
	 */
	public List<MetroLineIntervalSchedule> findIntervalLrSchedule(Long intervalId, String leftOrRight,
			String startDateStr, String endDateStr, String cycleType, boolean isFill);
	/**
	 * 保存计划进度信息
	 * 
	 * @param intervalId
	 *            区间ID
	 * @param leftOrRight
	 *            左、右线
	 * @param startDateStr
	 *            开始日期
	 * @param endDateStr
	 *            结束日期
	 * @param scheduleRingNum
	 *            计划日掘进环数
	 * @return
	 */
	public CommonResponse saveSchedule(Long intervalId, String leftOrRight, String startDateStr, String endDateStr,
			int scheduleRingNum);

	/**
	 * 取区间左右线某时间段，某周期类型的进度（计划进度、实际进度）
	 * @param intervalId
	 * @param leftOrRight
	 * @param beginDate
	 * @param endDate
	 * @param cycleType
	 * @return
	 */
	public List<List<Object>> getIntervalLrSchedule(Long intervalId, String leftOrRight,Date beginDate, Date endDate,String cycleType);
	
	
	/**
	 * 获取区间、左右线进度的基础信息
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	public Map<String, Object> getIntervalLrBase(Long intervalId, String leftOrRight);
}
