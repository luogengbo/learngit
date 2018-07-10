package com.szgentech.metro.dao;

import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroDailyReport;
/**
 * 盾构施工风险分析日报表 dao
 * @author luohao
 *
 */
public interface IMetroDailyReportDao extends BaseDao<MetroDailyReport> {

/**
 * 查询盾构施工风险分析日报表数据,可根据区间、左右线、施工报表时间查询
 * @param params
 * @return
 */
	MetroDailyReport findDailyReport(Map<String, Object> params);
	
	int insertObjs(Map<String, Object> params);
}