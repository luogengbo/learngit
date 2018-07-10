package com.szgentech.metro.service;


import com.szgentech.metro.model.MetroUserRiskSegmentationRel;

/**
 * 用户与风险点的对应关系业务接口
 * @author luohao
 *
 */
public interface IMetroUserRiskSegmentationRelService {

    /**
     * 更新
     */
	public boolean updateObj(MetroUserRiskSegmentationRel userRiskRel);
	
	/**
	 * 删除
	 * 
	 * @param userRiskRecId  风险ID
	 * @return
	 */
	public boolean deleteObj(Long userRiskRecId);
	
	/**
	 * 插入用户与风险对应关系数据
	 * @param userId             用户Id
	 * @param riskSegmentationId 风险ID
	 * @param isUsed			  是否启用0否1是
	 * @param phoneNo			  联系电话
	 * @return
	 */
	public int insertUserRiskSegmentationRel(Long userId, Long riskSegmentationId, String isUsed, String  phoneNo);
	
	/**
	 * 查找
	 */
	public MetroUserRiskSegmentationRel findObjById(Long userRiskRecId);
	
}
