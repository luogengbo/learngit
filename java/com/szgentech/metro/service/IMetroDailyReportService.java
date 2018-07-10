package com.szgentech.metro.service;


import java.util.Date;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroDailyReport;
import com.szgentech.metro.vo.MonitorIntervalLrStaticView;

/**
 * 盾构施工风险分析日报表业务接口
 * @author luohao
 *
 */
public interface IMetroDailyReportService {


	MetroDailyReport findDailyReport(Long intervalId, String leftOrRight, Date reportTime);
	
	/**
	 * 更新线路区间日报表信息
	 * @param dailyReport
	 * @return
	 */
	boolean updateObj(MetroDailyReport dailyReport);
	/**
	 * 保存线路区间日报表信息
	 * @param dailyReport
	 * @return
	 */
	Long insertObj(MetroDailyReport dailyReport);
	/**
	 * 分页查询
	 * 线路日报表信息
	 * @param intervalId 线路区间id
	 * @param leftOrRight 区间左右线
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	public PageResultSet<MetroDailyReport> findDailyReportInfo(
			Long intervalId, String leftOrRight, String reportTime, int pageNum, int pageSize);
	
	/**
	 * 根据区间、左右线、日期查询环信息
	 * @param intervalId
	 * @param leftOrRight
	 * @param time
	 * @return
	 */
	public MonitorIntervalLrStaticView getStaticViewByTime(Long intervalId, String leftOrRight, Date time );

	/**
	 * 生成指定区间左右线、时间的PDF格式周报表
	 * @param intervalId  区间
	 * @param leftOrRight 左右线
	 * @param reportTime  报表时间
	 * @return
	 * @throws Exception
	 */
	public String exportDailyReportPdf(Long intervalId, String leftOrRight, Date reportTime);
}
