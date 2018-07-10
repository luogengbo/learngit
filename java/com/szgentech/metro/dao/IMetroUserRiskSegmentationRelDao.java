package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroUserRiskSegmentationRel;
/**
 * 用户与风险点的关系 dao
 * @author luohao
 *
 */
public interface IMetroUserRiskSegmentationRelDao extends BaseDao<MetroUserRiskSegmentationRel> {

	/**
	 * 通过风险id查找风险相关人员列表信息
	 * 
	 * @param params
	 * @return
	 */
	List<MetroUserRiskSegmentationRel> findObjsByRiskId(Map<String, Object> params);
	

}