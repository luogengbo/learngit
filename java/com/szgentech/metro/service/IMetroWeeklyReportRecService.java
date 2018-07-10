package com.szgentech.metro.service;


import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroWeeklyReportRec;

/**
 * 盾构周报表上传记录业务接口
 * @author luohao
 *
 */
public interface IMetroWeeklyReportRecService {

	MetroWeeklyReportRec findWeeklyReportRec(Long intervalId, String leftOrRight, String reportTime);
	
	/**
	 * 插入周报表记录信息
	 * @param weeklyReportRec
	 * @return
	 */
	public int addWeeklyReportRec(MetroWeeklyReportRec weeklyReportRec);
	
	/**
	 * 删除周报表记录信息
	 * @param weeklyReprotId
	 * @return
	 */
	boolean deleteObj(Long weeklyReprotId);
	
	/**
	 * 分页查询
	 * 周报表记录信息
	 * @param intervalId 线路区间id
	 * @param leftOrRight 区间左右线
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroWeeklyReportRec> findWeeklyReportInfo(Long intervalId, String leftOrRight, int pageNum, int pageSize);
	

}
