package com.szgentech.metro.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szgentech.metro.base.controller.BaseController;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroUserOperateRec;
import com.szgentech.metro.model.OperationRecord;
import com.szgentech.metro.model.UserRecordAnaly;
import com.szgentech.metro.service.IMetroOperateService;
import com.szgentech.metro.vo.MonitorLrAlldicView;

@Controller
@RequestMapping("/operate")
public class MetroOperateController extends BaseController {

	@Autowired
	private IMetroOperateService operateService;

	/**
	 * 操作记录管理主页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String list() {

		return "/sysm/smf_record";
	}

	/**
	 * 查找操作记录
	 * 
	 * @param pageNum
	 *            页号
	 * @param pageSize
	 *            每页项数
	 * @return 操作记录
	 */
	// @SysControllorLog(menu="操作记录管理",operate="查询操作记录")
	@RequestMapping("/find/operates")
	@ResponseBody
	public PageResultSet<MetroUserOperateRec> findOperateRecAll(@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize) {
		PageResultSet<MetroUserOperateRec> recordResult = operateService.findMetroOperateInfo(pageNum, pageSize);
		return recordResult;
	}
	
	/**
	 * 操作记录分析
	 * 
	 */
	@RequestMapping("/find/operatesList")
	@ResponseBody
	public List<OperationRecord> findOperateList(String starttime,String endtime) {
		List<OperationRecord> recordResult = operateService.operateList( starttime,  endtime);
		return recordResult;
	}
}
