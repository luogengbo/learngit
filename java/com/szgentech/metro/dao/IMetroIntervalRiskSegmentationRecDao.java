package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroIntervalRiskSegmentation;
import com.szgentech.metro.model.MetroIntervalRiskSegmentationRec;
/**
 * 地质风险划分阻段划分预警记录 dao
 * @author luohao
 *
 */
public interface IMetroIntervalRiskSegmentationRecDao extends BaseDao<MetroIntervalRiskSegmentationRec> {

	/**
	 * 查询地质风险划分阻段划分预警数据
	 * @param params
	 * @return
	 */
	List<MetroIntervalRiskSegmentationRec> findRiskRecByRiskId(Map<String, Object> params);
	
	/**
	 * 查询唯一风险点记录
	 * @param params
	 * @return
	 */
	MetroIntervalRiskSegmentationRec findUniqueriskSegmentationRec(Map<String, Object> params);

    
}