package com.szgentech.metro.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.szgentech.metro.background.vo.IntervalRiskSegmentationVO;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroIntervalRiskSegmentation;

/**
 * 盾构地质风险划分阻段划分业务接口
 * @author luohao
 *
 */
public interface IMetroIntervalRiskSegmentationService {


	List<MetroIntervalRiskSegmentation> findriskSegmentation(long intervalId, String leftOrRight);
	
	/**
	 * 更新地质风险划分阻段划分数据
	 * @param riskSegmentation
	 * @return
	 */
	boolean updateObj(MetroIntervalRiskSegmentation riskSegmentation);
	/**
	 * 保存地质风险划分阻段划分数据
	 * @param riskSegmentation
	 * @return
	 */
	Long insertObj(MetroIntervalRiskSegmentation riskSegmentation);
	/**
	 * 删除地质风险划分阻段划分数据
	 * @param riskId
	 * @return
	 */
	boolean deleteObj(Long riskId);
	/**
	 * 分页查询
	 * 地质风险划分阻段划分数据
	 * @param intervalId 线路区间id
	 * @param leftOrRight 左右线标识
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroIntervalRiskSegmentation> findriskSegmentationInfo(Long intervalId, String leftOrRight, int pageNum, int pageSize);
	/**
	 * 导入excel数据
	 * @param intervalId   区间id
	 * @param leftOrRight  左右线标识
	 * @param file      文件
	 * @return
	 * @throws Exception
	 */
	boolean importExcelData(String intervalId, String leftOrRight, MultipartFile file) throws Exception;
	
	boolean updateRiskImg(Long intervalRsId,String Img1Url,String Img2Url,String Img3Url);

	boolean updateImg(MetroIntervalRiskSegmentation rs);
	
	/**
	 * 通过id查询地质风险划分阻段划分信息
	 * @param riskId  
	 * @return
	 */
	MetroIntervalRiskSegmentation findObjById(Long riskId);
	
	/**
	 * 获取所有监测预警阀值设置信息的路线左右线列表，用于historian接口查询该路线对应左右线的环数
	 * @return
	 */
	public List<IntervalRiskSegmentationVO> findQueryVOListForAll();
	
	/**
	 * 获取某线路左右线的地质风险划分阻段划分信息列表，用于historian接口查询符合环数要求的数据
	 * @param intervalId 线路区间id
	 * @param leftOrRight 左右线标识
	 * @return
	 */
	public List<IntervalRiskSegmentationVO> findQueryVOList(Long intervalId, String leftOrRight);
	
	public void riskSegmentationTimerService();
}
