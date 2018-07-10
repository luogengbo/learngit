package com.szgentech.metro.service;

import java.util.Date;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroDailyreportRec;

/**
 * 盾构日报表上传记录业务接口
 * @author luohao
 */
public interface IMetroDailyReportRecService {

	MetroDailyreportRec findDailyReportRec(Long intervalId, String leftOrRight, Date reportTime);
	
	/**
	 * 插入日报表记录信息
	 * @param dailyReportRec
	 * @return
	 */
	public int addDailyReportRec(MetroDailyreportRec dailyReportRec);
	
	/**
	 * 删除日报表记录信息
	 * @param dailyReprotId
	 * @return
	 */
	boolean deleteObj(Long dailyReprotId);
	
	/**
	 * 分页查询
	 * 日报表记录信息
	 * @param intervalId 线路区间id
	 * @param leftOrRight 区间左右线
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroDailyreportRec> findDailyReportInfo(Long intervalId, String leftOrRight,String reportTime, int pageNum, int pageSize);
	
}
