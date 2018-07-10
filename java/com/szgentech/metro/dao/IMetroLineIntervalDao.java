package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroLineInterval;

/**
 * Created by pjc on 2016/12/26.
 */
public interface IMetroLineIntervalDao extends BaseDao<MetroLineInterval> {
	/**
	 * 查找线路的区间列表
	 * 
	 * @param lineId
	 * @return
	 */
	List<MetroLineInterval> findObjsForLine(Long lineId);

	/**
	 * 修改风险组段划分文档url
	 * 
	 * @param params
	 * @return
	 */
	int editRiskPdfUrl(Map<String, Object> params);

	/**
	 * 修改地质详勘url
	 * 
	 * @param params
	 * @return
	 */
	int editSurveyPdfUrl(Map<String, Object> params);
}
