package com.szgentech.metro.service;


import java.util.List;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.Homepage;
import com.szgentech.metro.model.MetroLineInterval;
import com.szgentech.metro.model.MetroUpdateApp;
import com.szgentech.metro.model.SettlingName;
import com.szgentech.metro.model.SettlingParticulars;


public interface IMetroSettlingService {

	/**
	 * APP沉降点查询(一次性返回所有数据)
	 * @param userId
	 * @return
	 */
	List<Homepage> getfindSettling(Long userId);
	
	/**
	 * APP沉降点名称查询
	 * @param intervalid
	 * @return
	 */
	List<SettlingName> findSettlingName(Long intervalid);
	
	/**
	 * APP沉降详情查询
	 * @return
	 */
	SettlingParticulars findSettlingParticulars(String spname);
	
	/**
	 * APP管片(查询线路区间的管片ID)
	 * @return
	 */
	MetroLineInterval findDuctId(Long intervalId);
	
	/**
	 * APP版本更新
	 * @return
	 */
	MetroUpdateApp MetroUpdateData(Integer facilityflag);
	
	/**
	 * APP历史上传版本
	 * @return
	 */
	PageResultSet<MetroUpdateApp> ListMetroUpdateData(Integer facilityflag, int pageNum, int pageSize);
	
	/**
	* 删除APP版本记录
	* @param warnRecId
	* @return
	*/
	boolean deleteObj(Integer warnRecId);
	
	/**
	 * 检测发布的版本是否重复
	 * @return
	 */
	boolean findMetroUpdateAppC(Integer upversioncode);
	
	/**
	 * APP新版本的发布
	 * @return
	 */
	boolean addMetroUpdateApp(String upAppname,Integer upversioncode,String upversionname,String upUpdateURL,String upUpdatingContent,Integer facilityflag,Integer upWhetherupdating);
}
