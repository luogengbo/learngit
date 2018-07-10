package com.szgentech.metro.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.szgentech.metro.model.MetroLineInterval;

/**
 * 地铁城市线路区间业务接口
 * 
 * @author hank
 *
 *         2016年8月25日
 */
public interface IMetroLineIntervalService {

	/**
	 * 保存线路区间信息
	 * 
	 * @param interval
	 * @param userId
	 * @return
	 */
	Long insertObj(MetroLineInterval interval, Long userId);

	/**
	 * 删除线路区间信息
	 * 
	 * @param intervalId
	 * @return
	 */
	boolean deleteObj(Long intervalId);

	/**
	 * 更新线路区间信息
	 * 
	 * @param interval
	 * @return
	 */
	boolean updateObj(MetroLineInterval interval);

	/**
	 * 通过id查询地铁城市线路区间信息
	 * 
	 * @param intervalId
	 * @return
	 */
	MetroLineInterval findObjById(Long intervalId);

	/**
	 * 修改风险组段划分文档url
	 * 
	 * @param intervalId
	 *            区间id用于地图
	 * @param pdfUrl
	 *            风险组段划分文档url
	 * @return
	 */
	boolean editRiskPdfUrl(Long intervalId, String pdfUrl);
	
	/**
	 * 修改地质详勘文档url
	 * 
	 * @param intervalId
	 *            区间id
	 * @param surveyPdfUrl
	 *            地质详勘文档url
	 * @return
	 */
	boolean editSurveyPdfUrl(Long intervalId, String surveyPdfUrl);


	/**
	 * 
	 * @param intervalId
	 *            区间Id
	 * @param leftOrRight
	 *            左右线
	 * @param date
	 *            日期
	 * @param ring
	 *            环
	 * @param key
	 * @param type
	 * @return
	 */
	Map<String, Object> getShieldData(Long intervalId, String leftOrRight, Date date, String ring, String key,
			String type);
	
	/**
	 * 查询多参数不同条件的盾构数据
	 * @param intervalId   区间
	 * @param leftOrRight  左右线
	 * @param date         日期
	 * @param iStartRing   开始环
	 * @param iEndRing     结束环
	 * @param dicNameList  参数列表
	 * @param type         查询方式，“ring”按换号差，“date”按日期查询
	 * @return
	 */
	public List<Map<String, Object>> getShieldData1(Long intervalId, String leftOrRight, 
			Date date, int iStartRing, int iEndRing, List<String> dicNameList, String type);

}
