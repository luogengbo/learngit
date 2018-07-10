package com.szgentech.metro.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.dao.IMetroUserRiskSegmentationRelDao;
import com.szgentech.metro.model.MetroUserRiskSegmentationRel;
import com.szgentech.metro.service.IMetroUserRiskSegmentationRelService;


/**
 * 用户与风险点的对应关系接口实现
 * @author luohao
 *
 */
@Service("userRiskSegmentationRelService")
public class MetroUserRiskSegmentationRelService extends BaseService<MetroUserRiskSegmentationRel> implements IMetroUserRiskSegmentationRelService {
	private static Logger logger = Logger.getLogger(MetroUserRiskSegmentationRelService.class);
	
	@Autowired
	private IMetroUserRiskSegmentationRelDao userRiskSegmentationRelDao;
	
    /**
     * 更新
     */
	@Override
	public boolean updateObj(MetroUserRiskSegmentationRel userRiskRel) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userRiskRecId", userRiskRel.getId());
		params.put("userId", userRiskRel.getUserId());
		params.put("riskSegmentationId", userRiskRel.getRiskSegmentationId());
		params.put("isUsed", userRiskRel.getIsUsed());
		params.put("phoneNo", userRiskRel.getPhoneNo());
		int r = userRiskSegmentationRelDao.updateObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 删除
	 * 
	 * @param userRiskRecId  风险ID
	 * @return
	 */
	@Override
	public boolean deleteObj(Long userRiskRecId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("riskId", userRiskRecId);
		int r = userRiskSegmentationRelDao.deleteObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 插入用户与风险对应关系数据
	 * @param userId             用户Id
	 * @param riskSegmentationId 风险ID
	 * @param isUsed			  是否启用0否1是
	 * @param phoneNo			  联系电话
	 * @return
	 */
	@Override
	public int insertUserRiskSegmentationRel(Long userId, Long riskSegmentationId, String isUsed, String  phoneNo) {
		MetroUserRiskSegmentationRel userRiskRel = new MetroUserRiskSegmentationRel();
		userRiskRel.setUserId(userId);
		userRiskRel.setRiskSegmentationId(riskSegmentationId);
		userRiskRel.setIsUsed(isUsed);
		userRiskRel.setPhoneNo(phoneNo);
		return userRiskSegmentationRelDao.insertObj(userRiskRel);
	}
	
	/**
	 * 查找
	 */
	@Override
	public MetroUserRiskSegmentationRel findObjById(Long userRiskRecId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userRiskRecId", userRiskRecId);
		return userRiskSegmentationRelDao.findObjById(params);
	}
	
}
