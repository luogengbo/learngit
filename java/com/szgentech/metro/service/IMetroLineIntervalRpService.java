package com.szgentech.metro.service;

import java.util.List;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroLineIntervalRp;

/**
 * 地铁线路区间风险位置业务接口
 * @author luowq
 *
 */
public interface IMetroLineIntervalRpService {

	/**
	 * 分页查询
	 * 风险位置信息
	 * @param intervalId 线路区间id
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroLineIntervalRp> findLineIntervalRpInfo(Long intervalId, int pageNum, int pageSize);



	List<MetroLineIntervalRp> findLineIntervalRpInfo(Long intervalId);

	
	/**
	 * 保存
	 * @param rp
	 * @return
	 */
	Long insertObj(MetroLineIntervalRp rp);
	/**
	 * 更新
	 * @param rp
	 * @return
	 */
	boolean updateObj(MetroLineIntervalRp rp);

	/**
	 * 通过id查询风险位置信息
	 * @param intervalRpId
	 * @return
	 */
	MetroLineIntervalRp findObjById(Long intervalRpId);

	/**
	 * 删除风险位置信息
	 * @param intervalRpId
	 * @return
	 */
	boolean deleteObj(Long intervalRpId);

	boolean updateRiskImg(Long intervalRpId,String imgUrl);

	boolean updateRiskPdf(Long intervalRpId,String pdf1Url,String pdf2Url,String pdf3Url);

	boolean updatePdf(MetroLineIntervalRp rp);

}
