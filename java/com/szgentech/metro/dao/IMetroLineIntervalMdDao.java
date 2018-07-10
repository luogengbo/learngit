package com.szgentech.metro.dao;

import java.util.List;

import com.szgentech.metro.base.dao.BaseDao;
import com.szgentech.metro.model.MetroLineIntervalMd;

/**
 * 地铁线路区间沉降点监测数据dao
 * @author hank
 *
 * 2016年8月16日
 */
public interface IMetroLineIntervalMdDao extends BaseDao<MetroLineIntervalMd> {
	
	/**
	 * 批量插入沉降点监测数据记录
	 * @param list
	 * @return
	 */
	int insertObjs(List<MetroLineIntervalMd> list);
	/**
	 * 删除某次上传的文件监测数据记录
	 * @param intervalMdReId
	 * @return
	 */
	int deleteByIntervalMdReId(Long intervalMdReId);
}
