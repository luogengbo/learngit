package com.szgentech.metro.service;

import com.szgentech.metro.base.model.CommonResponse;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroLineIntervalMdRe;

/**
 * 地铁线路区间上传监测数据记录业务接口
 * @author luowq
 *
 */
public interface IMetroLineIntervalMdReService {
	/**
	 * 分页查询
	 * 上传监测数据记录信息
	 * @param intervalId 线路区间id
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroLineIntervalMdRe> findLineIntervalMdReInfo(Long intervalId, int pageNum, int pageSize);
	
	/**
	 * 沉降点监测数据上传
	 * @param intervalId 区间ID
	 * @param uploadFileUrl  文件url
	 * @return
	 */
	CommonResponse uploadLineIntervalMdReData(Long intervalId,String uploadFileUrl);
	
	/**
	 * 沉降点监测数据记录删除
	 * @param intervalMdReId
	 * @return
	 * @throws Exception
	 */
	boolean deleteLineIntervalMdReInfo(Long intervalMdReId) throws Exception;
}
