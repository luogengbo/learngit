package com.szgentech.metro.service;

import java.util.Date;
import java.util.List;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.DaiyWarnStatistics;
import com.szgentech.metro.model.MetroLineIntervalTota;
import com.szgentech.metro.model.MetroLineIntervalWarningRec;
import com.szgentech.metro.model.WarnRiskForecastEntry;

/**
 * 监测预警记录业务接口
 * @author MAJL
 *
 */
public interface IMetroWarningRecService {
	
	/**
	 * 主页铃铛查询
	 * @param userId
	 * @param p_date
	 * @return
	 */
	List<MetroLineIntervalWarningRec> findLastWarningRecs(Long userId, Date p_date);

	/**
	 * 主页铃铛查询
	 * @param intervalId
	 * @param leftOrRight
	 * @param p_date
	 * @return
	 */
	List<MetroLineIntervalWarningRec> findLastWarningRecsIntervalId(Long intervalId,String leftOrRight, Date p_date);
	
	/**
	 * 监测预警分页查询
	 * @param userId 用户Id
	 * @param intervalId 区间Id
	 * @param leftOrRight 左右线标记
	 * @param pageNum 当前页码
	 * @param pageSize 单页记录数
	 * @param confirmLevel 是否已人工确认预警等级
	 * @param isPush  是否已推送
	 * @return
	 */
	PageResultSet<MetroLineIntervalWarningRec> findWarningRecs(Long userId, String intervalId, String leftOrRight,
    int pageNum, int pageSize, String beginTime, String endTime, String warnParam, Integer confirmLevel, Integer isPush);
	
	/**
	 * 批量插入监测预警数据记录
	 * @param list
	 * @return
	 */
	boolean insertObjs(List<MetroLineIntervalWarningRec> list) throws Exception;
	
	/**
	 * APP风险预报
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	WarnRiskForecastEntry findWarnRiskForecast(Integer userId,int pageNum,int pageSize);
	/**
	 * 是否人工确认预警等级或是否推送
	 * 
	 * @param id
	 *            id
	 * @param confirmLevel
	 *            是否已人工确认预警等级
	 * @param isPush
	 *            是否已推送
	 * @return
	 */
	 boolean editConfirmInfo(Long id, Integer confirmLevel, Integer isPush, Integer warningLevel);
	 
	/**
	* 删除预警记录信息
	* @param warnRecId
	* @return
	*/
	boolean deleteObj(Long warnRecId);
	
	/***
	 * （单个参数总计、已确认等级数、已推送预警短信数(响应)）
	 * @return
	 */
	public List<DaiyWarnStatistics> findDaiyWarnStatistics(Long intervalId, String leftOrRight, Date beginTime, Date reportTime);
	
	/**
	 * 监测预警分析统计
	 * @param intervalId 区间Id
	 * @param leftorright 左右线标记
	 * @param starttime 开始时间
	 * @param endtime 结束时间
	 * @return
	 */
	public MetroLineIntervalTota findWarningTotal(String intervalId, String leftorright,String starttime,String endtime);

}
