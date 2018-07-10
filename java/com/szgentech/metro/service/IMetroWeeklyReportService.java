package com.szgentech.metro.service;


import java.util.Date;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroWeeklyReport;
import com.szgentech.metro.vo.MonitorIntervalLrStaticView;

/**
 * 盾构施工风险分析周报表业务接口
 * @author luohao
 *
 */
public interface IMetroWeeklyReportService {


	MetroWeeklyReport findWeeklyReport(Long intervalId, String leftOrRight, Date reportTime);
	
	/**
	 * 更新线路区间周报表信息
	 * @param weeklyReport
	 * @return
	 */
	boolean updateObj(MetroWeeklyReport weeklyReport);
	/**
	 * 保存线路区间周报表信息
	 * @param weeklyReport
	 * @return
	 */
	Long insertObj(MetroWeeklyReport weeklyReport);
	/**
	 * 分页查询
	 * 线路周报表信息
	 * @param intervalId 线路区间id
	 * @param leftOrRight 区间左右线
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	public PageResultSet<MetroWeeklyReport> findReportInfo(
			Long intervalId, String leftOrRight, String reportTime, int pageNum, int pageSize);
	
	/**
	 * 根据区间、左右线、日期查询环信息
	 * @param intervalId   区间id
	 * @param leftOrRight  左右线
	 * @param beginTime    开始时间
	 * @param beginTime    结束时间
	 * @return
	 */
	public MonitorIntervalLrStaticView getStaticViewByTime(Long intervalId, String leftOrRight, Date beginTime, Date endTime );
	/**
	 * 根据区间、左右线、开始日期和结束日期查询每周环信息
	 * @param intervalId  区间id
	 * @param leftOrRight 区间左右线
	 * @param beginTime   开始时间
	 * @param beginTime   结束时间
	 * @return
	 */
	public MonitorIntervalLrStaticView getWeeklyViewByTime(Long intervalId, String leftOrRight, Date beginTime, Date endTime );

	/**
	 * 生成指定区间左右线、时间的PDF格式周报表
	 * @param intervalId  区间
	 * @param leftOrRight 左右线
	 * @param endTime  报表时间
	 * @return
	 */
	public String exportWeeklyReportPdf(Long intervalId, String leftOrRight, Date endTime);
}
