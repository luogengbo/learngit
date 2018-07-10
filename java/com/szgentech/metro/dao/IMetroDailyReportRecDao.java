package com.szgentech.metro.dao;

import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroDailyreportRec;
/**
 * 盾构日报表上传记录 dao
 * @author luohao
 *
 */
public interface IMetroDailyReportRecDao extends BaseDao<MetroDailyreportRec> {

/**
 * 查询盾构日报表记录,可根据区间、左右线、施工报表时间查询
 * @param params
 * @return
 */
	MetroDailyreportRec findDailyReportRec(Map<String, Object> params);
	
}