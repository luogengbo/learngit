package com.szgentech.metro.service;


import java.util.List;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroIntervalRiskSegmentationRec;

/**
 * 盾构地质风险划分阻段划分预警记录业务接口
 * @author luohao
 *
 */
public interface IMetroIntervalRiskSegmentationRecService {

	/**
	 * 查找地质风险划分阻段划分预警记录
	 */
	public List<MetroIntervalRiskSegmentationRec> findRiskSegmentationRec(Long riskId, int start, int end);
	/**
	 * 分页查询
	 * 地质风险划分阻段划分预警记录
	 * @param intervalId 线路区间id
	 * @param leftOrRight 左右线标识
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	public PageResultSet<MetroIntervalRiskSegmentationRec> findriskSegmentationRecInfo(Long intervalId, String leftOrRight, int pageNum, int pageSize);
    /**
     * 更新地质风险划分阻段划分数据
     */
	public boolean updateObj(MetroIntervalRiskSegmentationRec riskSegmentationRec);
	
	/**
	 * 删除地质风险划分阻段划分预警记录
	 * 
	 * @param riskRecId
	 * @return
	 */
	public boolean deleteObj(Long riskRecId);
	
	/**
	 * 插入风险点预警记录
	 * @param intervalId   区间ID
	 * @param leftOrRight  左右线
	 * @param riskId       风险点配置ID
	 * @param ringNum      当前环号
	 * @return
	 */
	public int insertSegmentationRec(Long intervalId, String leftOrRight, Long riskId, Integer ringNum);
	
	public MetroIntervalRiskSegmentationRec findObjById(Long riskRecId);
	
}
