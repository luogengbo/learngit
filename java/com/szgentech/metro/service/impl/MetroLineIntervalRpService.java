package com.szgentech.metro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.dao.IMetroLineIntervalRpDao;
import com.szgentech.metro.model.MetroLineIntervalRp;
import com.szgentech.metro.service.IMetroLineIntervalRpService;

/**
 * 地铁线路区间工程进度业务接口实现
 * 
 * @author hank
 *
 *         2016年9月1日
 */
@Service("lineIntervalRpService")
public class MetroLineIntervalRpService extends BaseService<MetroLineIntervalRp> implements IMetroLineIntervalRpService {

	// private static Logger logger = Logger.getLogger(MetroLineIntervalRpService.class);

	@Autowired
	private IMetroLineIntervalRpDao lineIntervalRpDao;

	@Override
	public PageResultSet<MetroLineIntervalRp> findLineIntervalRpInfo(Long intervalId, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		PageResultSet<MetroLineIntervalRp> resultSet = getPageResultSet(params, pageNum, pageSize, lineIntervalRpDao);
		return resultSet;
	}

	@Override
	public List<MetroLineIntervalRp> findLineIntervalRpInfo(Long intervalId) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		List<MetroLineIntervalRp> rps = lineIntervalRpDao.findObjsList(params);
		return rps;
	}

	/**
	 * 保存
	 */
	@Override
	public Long insertObj(MetroLineIntervalRp rp) {
		int r = lineIntervalRpDao.insertObj(rp);
		if (r > 0) {
			return rp.getId();
		}
		return null;
	}

	/**
	 * 更新
	 */
	@Override
	public boolean updateObj(MetroLineIntervalRp rp) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalRpId", rp.getId());
		params.put("intervalId", rp.getIntervalId());
		params.put("positionNo", rp.getPositionNo());
		params.put("leftOrRight", rp.getLeftOrRight());
		params.put("gType", rp.getgType());
		params.put("textMsg", rp.getTextMsg());
		params.put("riskImgUrl", rp.getRiskImgUrl());
		params.put("riskPdf1Url", rp.getRiskPdf1Url());
		params.put("riskPdf2Url", rp.getRiskPdf2Url());
		params.put("riskPdf3Url", rp.getRiskPdf3Url());
		int r = lineIntervalRpDao.updateObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 更新
	 */
	@Override
	public boolean updatePdf(MetroLineIntervalRp rp) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalRpId", rp.getId());
		params.put("riskPdf1Url", rp.getRiskPdf1Url());
		params.put("riskPdf2Url", rp.getRiskPdf2Url());
		params.put("riskPdf3Url", rp.getRiskPdf3Url());
		int r = lineIntervalRpDao.updatePdf(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public MetroLineIntervalRp findObjById(Long intervalRpId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalRpId", intervalRpId);
		return lineIntervalRpDao.findObjById(params);
	}

	/**
	 * 删除风险位置信息
	 * 
	 * @param intervalRpId
	 * @return
	 */
	@Override
	public boolean deleteObj(Long intervalRpId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalRpId", intervalRpId);
		int r = lineIntervalRpDao.deleteObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 更新风险位置信息的图片信息
	 * 
	 * @param intervalRpId，imgUrl
	 * @return
	 */
	@Override
	public boolean updateRiskImg(Long intervalRpId, String imgUrl) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalRpId", intervalRpId);
		MetroLineIntervalRp rp = lineIntervalRpDao.findObjById(params);
		rp.setRiskImgUrl(imgUrl);
		return updateObj(rp);
	}

	@Override
	public boolean updateRiskPdf(Long intervalRpId, String pdf1Url, String pdf2Url, String pdf3Url) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalRpId", intervalRpId);
		MetroLineIntervalRp rp = lineIntervalRpDao.findObjById(params);
		rp.setRiskPdf1Url(pdf1Url);
		rp.setRiskPdf2Url(pdf2Url);
		rp.setRiskPdf3Url(pdf3Url);
		return updatePdf(rp);
	}

}
