package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.DaiyWarnStatistics;
import com.szgentech.metro.model.MetroLineIntervalWarningRec;
import com.szgentech.metro.model.WarnRiskForecastEntry;
/**
 * 监测预警记录数据操作接口
 * @author MAJL
 *
 */
public interface IMetroWarningRecDao extends BaseDao<MetroLineIntervalWarningRec> {
	
	/**
	 * 监测预警记录查询 每次查询大于一分钟前新增的预警记录
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalWarningRec> findLastWarningRecs(
			Map<String, Object> params);

	/**
	 * 监测预警记录查询 每次查询大于一分钟前新增的预警记录
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalWarningRec> findLastWarningRecsByIntervalId(
			Map<String, Object> params);

	/**
	 * 批量插入监测预警数据记录
	 * @param list
	 * @return
	 */
	int insertObjs(List<MetroLineIntervalWarningRec> list);
	
	/**
	 * APP风险预报
	 * @param params
	 * @return
	 */
	WarnRiskForecastEntry findWarnRiskForecast(Map<String, Object> params);
	
	 /**
     * 确认预警等级与是否推送
     * @param params
     * @return
     */
    int editConfirmInfo(Map<String,Object> params);
    
    /**
   	 * 查询每天预警记录统计（单个参数总计、已确认等级数、已推送预警短信数(响应)）
   	 * @param params
   	 * @return
   	 */
   	List<DaiyWarnStatistics> findDaiyWarnStatistics(Map<String, Object> params);
   	
   	/**
   	 * 预警分析统计
   	 * @param params
   	 * @return
   	 */
   	List<MetroLineIntervalWarningRec> findWarningTotal(Map<String, Object> params);
}
