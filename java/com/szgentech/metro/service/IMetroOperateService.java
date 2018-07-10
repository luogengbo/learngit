package com.szgentech.metro.service;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroUserOperateRec;
import com.szgentech.metro.model.OperationRecord;
import com.szgentech.metro.model.UserRecordAnaly;
import com.szgentech.metro.vo.MonitorLrAlldicView;

/**
 * 用户操作记录业务处理接口
 * @author MAJL
 *
 */
public interface IMetroOperateService {
	/**
	 * 分页查询
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroUserOperateRec> findMetroOperateInfo(int pageNum, int pageSize);
	
	/**
	 * 批量入库操作日志
	 * @param logs
	 * @return
	 */
	boolean addLogs(List<MetroUserOperateRec> logs);
	
	
	/**
	 * @param starttime 开始时间
	 * @param endtime   结束时间
	 * @return
	 */
	List<OperationRecord> operateList(String starttime, String endtime);
}
