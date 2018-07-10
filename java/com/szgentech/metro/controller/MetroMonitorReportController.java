package com.szgentech.metro.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szgentech.metro.base.controller.BaseController;
import com.szgentech.metro.base.model.CommonResponse;
import com.szgentech.metro.base.utils.ConfigProperties;
import com.szgentech.metro.base.utils.JsTreeUtil;
import com.szgentech.metro.model.MetroCity;
import com.szgentech.metro.model.MetroLine;
import com.szgentech.metro.model.MetroLineInterval;
import com.szgentech.metro.service.IMetroLineIntervalService;
import com.szgentech.metro.service.IMetroLineService;
import com.szgentech.metro.service.ISysRightService;
import com.szgentech.metro.vo.Jstree;

@Controller
@RequestMapping("/monitor/report")
public class MetroMonitorReportController extends BaseController {

	@Autowired
	private ISysRightService rightService;

	@Autowired
	private IMetroLineService lineService;

	@Autowired
	private IMetroLineIntervalService intervalService;

	@RequestMapping("/index")
	public String list() {
		return "/find-info/info_monitor_report";
	}

	@RequestMapping("/to/report")
	public String toReport(@RequestParam("intervalId") Long intervalId, @RequestParam("lineId") Long lineId) {

		modelMap.addAttribute("intervalId", intervalId);
		modelMap.addAttribute("lineId", lineId);

		return "/find-info/info_monitor_report_ring";
	}

	@RequestMapping("/ring/export")
	@ResponseBody
	public CommonResponse exportRing(@RequestParam("intervalId") Long intervalId, @RequestParam("lineId") Long lineId,
			@RequestParam("leftOrRight") String leftOrRight, @RequestParam("ring") String ring,
			@RequestParam("cityName") String cityName) {

		CommonResponse commonResponse = new CommonResponse();

		MetroLine line = lineService.findObjById(lineId);
		MetroLineInterval interval = intervalService.findObjById(intervalId);

		String fileName = cityName + line.getLineNo() + "_" + interval.getIntervalMark() + leftOrRight + "_" + ring + ".pdf";
		String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
		File checkFile = new File(uploadPath + "/" + cityName + "/" + fileName);
		if (checkFile.exists()) {
			commonResponse.setCode(1);
			commonResponse.setResult(cityName + "/" + fileName);
		} else {
			commonResponse.setCode(0);
			commonResponse.setResult("不存在");
		}
		return commonResponse;
	}

	@RequestMapping("/tree-data/get")
	@ResponseBody
	public List<Jstree> getTreeData() {
		MetroCity city = rightService.getRightDatasByUserId(getCurrentUser().getId());
		String[] urls = new String[4];
		urls[2] = "/monitor/report/to/report";
		Boolean[] diss = new Boolean[3];
		diss[0] = true;
		diss[1] = true;
		diss[2] = false;
		return JsTreeUtil.getTreeDataForReport(request, city, urls, diss);
	}
}
