package com.szgentech.metro.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroLineIntervalSt;

/**
 * 地铁线路区间盾尾间隙业务接口
 * @author luowq
 *
 */
public interface IMetroLineIntervalStService {
	/**
	 * 保存区间盾尾间隙信息
	 * @param st
	 * @return
	 */
	Long insertObj(MetroLineIntervalSt st);
	/**
	 * 通过id查询盾尾间隙信息
	 * @param intervalStId
	 * @return
	 */
	MetroLineIntervalSt findObjById(Long intervalStId);
	/**
	 * 删除盾尾间隙信息
	 * @param intervalStId
	 * @return
	 */
	boolean deleteObj(Long intervalStId);
	/**
	 * 删除区间、左右盾尾间隙全部信息
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	public boolean deleteStInfo(Long intervalId, String leftOrRight);
	/**
	 * 更新盾尾间隙信息
	 * @param st
	 * @return
	 */
	boolean updateObj(MetroLineIntervalSt st);
	/**
	 * 分页查询
	 * 线路区间盾尾间隙信息
	 * @param intervalId 线路区间id
	 * @param leftOrRight 区间左右线
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroLineIntervalSt> findLineIntervalStInfo(Long intervalId, String leftOrRight, int pageNum, int pageSize);
	/**
	 * 查询某个区间的盾尾间隙信息
	 * @param intervalId
	 * @return
	 */
	List<MetroLineIntervalSt> findLineIntervalSts(Long intervalId);

	/**
	 * 导入excel数据
	 * @param intervalId
	 * @param file
	 * @return
	 * @throws Exception
	 */
	boolean importExcelData(String intervalId, MultipartFile file) throws Exception;
	/**
	 * 根据条件查询盾尾间隙数据
	 * @param intervalId   区间ID
	 * @param leftOrRight  左右线，左线："l",右线："r" 
	 *@param ringNum 环号
	 * @return
	 */
	public MetroLineIntervalSt findLineIntervalSt(Long intervalId, String leftOrRight, int ringNum);
}
