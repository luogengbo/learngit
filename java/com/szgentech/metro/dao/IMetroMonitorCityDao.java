package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.model.KeyValue;
import com.szgentech.metro.model.MetroLineIntervalMd;
import com.szgentech.metro.model.MetroLineIntervalWarningRec;
import com.szgentech.metro.model.MonitorInfoCity;
import com.szgentech.metro.model.MonitorIntervalData;
import com.szgentech.metro.model.MonitorIntervalSettlementPoint;

/**
 * 盾构信息监控数据查询接口
 * @author MAJL
 *
 */
public interface IMetroMonitorCityDao {
	
	/**
	 * 统计记录数
	 * @param params
	 * @return
	 */
	int countMonitorInfoCityDatas(Map<String, Object> params);
	
	/**
	 * city分页查询
	 * @param params
	 * @return
	 */
	List<MonitorInfoCity> findMonitorInfoCityDatas(Map<String, Object> params);

	/**
	 * city查询
	 * @param params
	 * @return
	 */
	List<MonitorInfoCity> findAllMonitorInfoCityDatas(Map<String, Object> params);
	
	/**
	 * 城市线路区间名称查询
	 * @param params
	 * @return
	 */
	MonitorInfoCity findIntervalMonitorInfoDatas(Map<String, Object> params);
	
	/**
	 * 城市盾构机运行状态统计
	 * @param params
	 * @return
	 */
	List<KeyValue> findCountMechineDatas(Map<String, Object> params);
	
	/**
	 * 区间左右线信息
	 * @param params
	 * @return
	 */
	MonitorInfoCity findMonitorInfoCity(Map<String, Object> params);
	
	/**
	 * 查询沉降点检测数据
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalMd> findMonitorInfoIntervalData(
			Map<String, Object> params);
	
	/**
	 * 查询沉降点检测数据
	 * @param params
	 * @return
	 */
	List<MonitorIntervalData> findMonitorIntervalMonitorData(Map<String, Object> params);

	/**
	 * 通过mdNo查询沉降点检测数据
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalMd> findMonitorInfoIntervalSettle(
			Map<String, Object> params);
	/**
	 * APP查询综合数据
	 * @param params
	 * @return
	 */
	MonitorInfoCity findPandectDao(Map<String, Object> params);
	
	/**
	 * 查询预警级别
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalWarningRec> findWarningLevel(Map<String, Object> params);

	/**
	 * 多个沉降点在同一曲线
	 * @param params
	 * @return
	 */
	List<MonitorIntervalSettlementPoint> findMonitorIntervalMonitorDataPoint(Map<String, Object> params);
	
	/**
	 * 查询线路工程信息，查询条件lineId
	 * @param params
	 * @return
	 */
	List<MonitorInfoCity> findMonitorInfoDatas(Map<String, Object> params);
	
	/**
	 * 查询线路区间信息，查询条件lineId
	 * @param lineId 线路编号
	 * @return
	 */
	List<MonitorInfoCity> findIntervalInfo(Map<String, Object> params);
	
	/**
	 * 获取当前环
	 * @return
	 */
	List<MonitorInfoCity> findMonitorInfoCityLoop(Map<String, Object> params);
	
	
	
}
