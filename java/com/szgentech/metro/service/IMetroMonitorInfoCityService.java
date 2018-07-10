package com.szgentech.metro.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroLoopMark;
import com.szgentech.metro.model.MonitorInfoCity;
import com.szgentech.metro.model.MonitorIntervalDataView;
import com.szgentech.metro.model.Pandect;
import com.szgentech.metro.vo.MonitorIntervalLrStaticView;
import com.szgentech.metro.vo.MonitorIntervalLrStaticsView;
import com.szgentech.metro.vo.MonitorIntervalView;
import com.szgentech.metro.vo.MonitorLrAlldicView;
import com.szgentech.metro.vo.MonitorViewData;

/**
 * 盾构信息监控城市业务接口
 * 
 * @author MAJL
 *
 */
public interface IMetroMonitorInfoCityService {
	/**
	 * 加条件分页查询
	 * 
	 * @param cityId
	 *            城市ID
	 * @param buildStatus
	 *            工程状态
	 * @param pageNum
	 *            页码
	 * @param lineId
	 *            线路Id
	 * @param pageSize
	 *            单页记录数
	 * @param userId
	 *             用户ID
	 * @param intervalName
	 *             区间名称
	 * @return
	 */
	PageResultSet<MonitorViewData> findMonitorInfoCityData(Long cityId, Long lineId, int buildStatus, String intervalName, int pageNum,
			int pageSize,Long userId) throws IOException;

	/**
	 * 城市盾构机工作状态统计
	 * 
	 * @param userId
	 *            用户Id
	 * @param lineId
	 *            线路Id
	 * @return
	 */
	Map<String, String> findCountMechineDatas(Long userId, Long lineId);

	/**
	 * 区间左右线数据
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	Map<String, Object> findIntervalLrDaoPanDatas(Long intervalId, String leftOrRight);

	/**
	 * 泥水盾构实时数据
	 * 
	 * @param intervalId
	 *            区间id
	 * @param leftOrRight
	 *            左右线
	 * @return 泥水盾构画面所需要的实时数据
	 */
	Map<String, Object> getSlurryData(Long intervalId, String leftOrRight);

	/**
	 * 刀盘SVG实时数据
	 * 
	 * @param intervalId
	 *            区间id
	 * @param leftOrRight
	 *            左右线
	 * @return 刀盘SVG画面所需要的实时数据
	 */
	Map<String, Object> getKnifeData(Long intervalId, String leftOrRight);

	/**
	 * 刀盘SVG实时数据
	 * 
	 * @param intervalId
	 *            区间id
	 * @param leftOrRight
	 *            左右线
	 * @return 刀盘SVG画面所需要的实时数据
	 */
	Map<String, Object> getSpiralData(Long intervalId, String leftOrRight);

	/**
	 * 查询所有参数数据
	 * 
	 * @param parseLong
	 * @param leftOrRight
	 * @return
	 */
	PageResultSet<MonitorLrAlldicView> findMonitorIntervalLrDics(long parseLong, String leftOrRight);

	/**
	 * 查询检测数据
	 * 
	 * @param intervalId
	 * @param intervalSpId
	 * @return
	 */
	MonitorIntervalView findMonitorIntervalDatas(Long intervalId, Long intervalSpId);
	
	/**
	 * 查询检测数据
	 * 
	 * @param intervalId 区间id
	 * @param date 日期 2017-12-07
	 */
	MonitorIntervalDataView findIntervalMonitorData(Long intervalId,String date,String leftOrRight);

	/**
	 * 获取区间左右线导向数据
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	Map<String, Object> findIntervalLrDaoxDatas(Long intervalId, String leftOrRight);

	/**
	 * 材料消耗统计
	 * 
	 * @param intervalId
	 *            区间ID
	 * @param leftOrRight
	 *            左右线标记
	 * @param beginRing
	 *            开始环号
	 * @param endRing
	 *            结束环号
	 * @param paramName
	 *            参数名
	 * @return
	 */
	List<List<Object>> findMonitorStaticTab1(String intervalId, String leftOrRight, String beginRing, String endRing,
			String paramName);

	/**
	 * 时间统计
	 * 
	 * @param intervalId
	 *            区间ID
	 * @param leftOrRight
	 *            左右线标记
	 * @param beginRing
	 *            开始环号
	 * @param endRing
	 *            结束环号
	 * @param type
	 *            统计类型
	 * @return
	 */
	List<List<Object>> findMonitorStaticTab2(String intervalId, String leftOrRight, String beginRing, String endRing, String type);

	/**
	 * 进度统计
	 * 
	 * @param intervalId
	 *            区间ID
	 * @param leftOrRight
	 *            左右线标记
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	List<List<Object>> findMonitorStaticTab3(String intervalId, String leftOrRight, Date beginDate, Date endDate);

	/**
	 * 汇总统计
	 * 
	 * @param intervalId
	 *            区间Id
	 * @param leftOrRight
	 *            左右线标记
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            单页记录数
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param excelType
	 *            报表类型1日,2周,3月
	 * @return
	 */
	PageResultSet<MonitorIntervalLrStaticView> findMonitorStaticTab4(Long intervalId, String leftOrRight,
			int pageNum, int pageSize, Date beginTime, Date endTime, String excelType);

	/**
	 * 综合分析
	 * 
	 * @param model
	 *            模式 1 模式 2 .. 模式7 或0不以模式查询此时ks无用
	 * @param type
	 *            指定以查询条件（t日期或r环号）
	 * @param beginTime
	 *            开始日期
	 * @param endTime
	 *            结束日期
	 * @param beginRing
	 *            开始环号
	 * @param endRing
	 *            结束环号
	 * @param ks
	 *            参数名称集合
	 * @param kns
	 *            参数中文名
	 * @return
	 */
	MonitorIntervalLrStaticsView findMonitorStaticTab5(String intervalId, String leftOrRight, int model, String type,
			Integer dataModel, Integer dataType, Date beginTime, Date endTime, int beginRing, int endRing, String[] ks,
			String[] kns, String[] indxs);

	/**
	 * 获取当前环号
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	int findCurrRingNum(Long intervalId, String leftOrRight);
	
	/**
	 * 
	 * @param lineName
	 * @param intervalName
	 * @param leftOrRight
	 */
	List<Pandect> findPandect(String lineName,String intervalName,String leftOrRight) throws IOException;
	
	/**
	 * 根据区间左右线到history获取实际进度
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Object> getActualProgress(Long intervalId, String leftOrRight, Date beginDate, Date endDate);
	
	/**
	 * 获取线路某时间段内的进度信息
	 * @param lineId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> getLineProcess(Long lineId, Date beginDate, Date endDate);
	
	/**
	 * 查询字典信息并按单位分组
	 * @return
	 */
	public Map<String,Object> getDictionaryGroup();
	
	/**
	 * 获取线路编号和
	 * @param lineName：线路名称
	 * @param intervalName：区间名称
	 * @param leftOrRight：左右线
	 * @return
	 * @throws IOException
	 */
	MetroLoopMark findMonitorInfoCityLoop(String lineName, String intervalName, String leftOrRight)throws IOException;

	/**
	 * 查询所有参数数据
	 * 
	 * @param parseLong
	 * @param leftOrRight
	 * @return
	 */
	PageResultSet<MonitorLrAlldicView> findMonitorIntervalLrDics2(long parseLong, String leftOrRight);
	
	/**
	 * 获取区间左右线通信状态
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	public int getCommuniStatus(Long intervalId, String leftOrRight);
	
	/**
	 * 获取城市线路区间名称
	 * @param intervalId 区间
	 * @param leftOrRight 左右线
	 * @return
	 * 
	 */
	public MonitorInfoCity findIntervalMonitorInfoDatas(Long intervalId,String leftOrRight);
	/**
	 * 区间左右线信息APP（分组基础参数））
	 * @param intervalId 区间
	 * @param leftOrRight 左右线
	 * @return
	 */
	Map<String, Object> getBaseData(Long intervalId, String leftOrRight);
	
	/**
	 * 取区间左右线指定环号最大值和最小值
	 * @param intervalId
	 * @param leftOrRight
	 * @param beginRing
	 * @param endRing
	 * @return
	 */
	public Map<String, Object> getMaxAndMinValue(Long intervalId, String leftOrRight, Integer beginRing, Integer endRing);
	
	/**
	 * 取区间左右线一周指定环号最大值和最小值
	 * @param intervalId
	 * @param leftOrRight
	 * @param beginRing
	 * @param endRing
	 * @return
	 */
	public Map<String, Object> getWeekMaxAndMinValue(Long intervalId, String leftOrRight, Integer beginRing, Integer endRing);

	/**
	 * 根据区间左右线、开始时间、结束时间获取某段时间推进的开始环和结束环数与推进环数
	 * @param intervalId  区间
	 * @param leftOrRight 左右线
	 * @param startRingNo 开始时间
	 * @param endRingNo   结束时间
	 * @return
	 */
	public Map<String, Object> findRingNumInfo(Long intervalId, String leftOrRight, Date beginTime, Date endTime);

}
