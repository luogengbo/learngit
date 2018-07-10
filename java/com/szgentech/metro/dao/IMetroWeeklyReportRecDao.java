package com.szgentech.metro.dao;

import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroWeeklyReportRec;
/**
 * 盾构周报表上传记录 dao
 * @author luohao
 *
 */
public interface IMetroWeeklyReportRecDao extends BaseDao<MetroWeeklyReportRec> {

/**
 * 查询盾构周报表记录,可根据区间、左右线、施工报表时间查询
 * @param params
 * @return
 */
	MetroWeeklyReportRec findWeeklyReportRec(Map<String, Object> params); 
	
}