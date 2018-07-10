package com.szgentech.metro.dao;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.background.vo.IntervalRiskSegmentationVO;
import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroIntervalRiskSegmentation;
/**
 * 地质风险划分阻段划分 dao
 * @author luohao
 *
 */
public interface IMetroIntervalRiskSegmentationDao extends BaseDao<MetroIntervalRiskSegmentation> {

	/**
	 * 查询地质风险划分阻段划分数据
	 * @param params
	 * @return
	 */
	List<MetroIntervalRiskSegmentation> findriskSegmentation(Map<String, Object> params);
	
	/**
	 * 查询唯一风险点数据
	 * @param params
	 * @return
	 */
	MetroIntervalRiskSegmentation findUniqueriskSegmentation(Map<String, Object> params);
	
	int insertObjs(Map<String, Object> params);
	
	  /**
     * 修改
     * @param params
     * @return
     */
    int updateImg(Map<String, Object> params);
    
    /**
	 * 获取所有地质风险划分阻段划分信息的路线左右线列表，用于historian接口查询该路线对应左右线的环数
	 * @return
	 */
	List<IntervalRiskSegmentationVO> findQueryVOListForAll();

	/**
	 * 获取某线路左右线的地质风险划分阻段划分信息列表，用于historian接口查询符合环数要求的数据
	 * @param params
	 * @return
	 */
	List<IntervalRiskSegmentationVO> findQueryVOList(Map<String, Object> params);
}