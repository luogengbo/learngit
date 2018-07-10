package com.szgentech.metro.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroLineIntervalData;
/**
 * 地铁线路区间导向数据业务接口
 * @author luowq
 *
 */
public interface IMetroLineIntervalDataService {
	/**
	 * 分页查询
	 * 导向数据信息
	 * @param intervalId 线路区间id
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroLineIntervalData> findLineIntervalDataInfo(Long intervalId, String leftOrRight, int pageNum, int pageSize);
	/**
	 * 删除导向数据信息
	 * @param intervalDataId
	 * @return
	 */
	boolean deleteObj(Long intervalDataId);
	
	/**
	 * 获取某线路区间左或右线的导向数据信息列表
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	List<MetroLineIntervalData> findAllByIntervalIdAndLr(Long intervalId, String leftOrRight);
	/**
	 * 导入excel数据
	 * @param intervalId
	 * @param leftOrRight
	 * @param file
	 * @return
	 * @throws Exception
	 */
	boolean importExcelData(String intervalId, String leftOrRight,
			MultipartFile file) throws Exception;
	/**
	 * 保存导向数据信息
	 * @param data
	 * @return
	 */
	Long insertObj(MetroLineIntervalData data);
	/**
	 * 更新导向数据信息
	 * @param data
	 * @return
	 */
	boolean updateObj(MetroLineIntervalData data);
	
	/**
	 * 删除区间、左右线导向数据
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	public boolean deleteDataInfo(Long intervalId, String leftOrRight);
}
