package com.szgentech.metro.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroLineIntervalSp;

/**
 * 地铁线路区间沉降点业务接口
 * @author luowq
 *
 */
public interface IMetroLineIntervalSpService {
	/**
	 * 保存区间沉降点信息
	 * @param sp
	 * @return
	 */
	Long insertObj(MetroLineIntervalSp sp);
	/**
	 * 通过id查询沉降点信息
	 * @param intervalSpId
	 * @return
	 */
	MetroLineIntervalSp findObjById(Long intervalSpId);
	/**
	 * 删除沉降点信息
	 * @param intervalSpId
	 * @return
	 */
	boolean deleteObj(Long intervalSpId);
	/**
	 * 更新沉降点信息
	 * @param sp
	 * @return
	 */
	boolean updateObj(MetroLineIntervalSp sp);
	/**
	 * 分页查询
	 * 线路区间沉降点信息
	 * @param intervalId 线路区间id
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroLineIntervalSp> findLineIntervalSpInfo(Long intervalId, int pageNum, int pageSize);
	/**
	 * 查询某个区间的沉降点信息
	 * @param intervalId
	 * @return
	 */
	List<MetroLineIntervalSp> findLineIntervalSps(Long intervalId);

	/**
	 * 导入excel数据
	 * @param intervalId
	 * @param file
	 * @return
	 * @throws Exception
	 */
	boolean importExcelData(String intervalId, MultipartFile file) throws Exception;
}
