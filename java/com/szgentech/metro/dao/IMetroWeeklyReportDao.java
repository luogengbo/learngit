package com.szgentech.metro.dao;

import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroWeeklyReport;
/**
 * 盾构施工风险分析周报表 dao
 * @author luohao
 *
 */
public interface IMetroWeeklyReportDao extends BaseDao<MetroWeeklyReport> {

/**
 * 查询盾构施工风险分析周报表数据,可根据区间、左右线、施工报表时间查询
 * @param params
 * @return
 */
	MetroWeeklyReport findWeeklyReport(Map<String, Object> params);
	
	int insertObjs(Map<String, Object> params);
}