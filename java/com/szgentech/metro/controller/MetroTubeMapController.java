package com.szgentech.metro.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szgentech.metro.base.controller.BaseController;
import com.szgentech.metro.model.MetroCity;
import com.szgentech.metro.model.MetroLine;
import com.szgentech.metro.service.IMetroTubeMapService;
import com.szgentech.metro.service.ISysRightService;
import com.szgentech.metro.vo.TubeMapIntervalLr;

/**
 * 线网图请求控制器
 * 
 * @author MAJL
 *
 */
@Controller
@RequestMapping("/tube/map")
public class MetroTubeMapController extends BaseController {

	@Autowired
	private ISysRightService rightService;

	@Autowired
	private IMetroTubeMapService tmService;

	/**
	 * 地铁线网图主页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String list() {

		return "/find-info/tube_map";
	}

	/**
	 * 查找线路数据
	 * 
	 * @return
	 */
	@RequestMapping("/find/line/datas")
	@ResponseBody
	public List<MetroLine> findUserDatasById() {
		MetroCity city = rightService.getRightDatasByUserId(this.getCurrentUser().getId());
		return city.getLineList();
	}

	/**
	 * 查找线路数据
	 * 
	 * @param intervalId
	 * @return 线路信息
	 */
	@RequestMapping("/find/line/interval/datas")
	@ResponseBody
	public Map<String, TubeMapIntervalLr> findUserDatasByIntervalId(@RequestParam("intervalId") Long intervalId) {

		TubeMapIntervalLr tm = tmService.findLrInfo(intervalId);
		Map<String, TubeMapIntervalLr> map = new HashMap<String, TubeMapIntervalLr>();
		map.put("data", tm);

		return map;
	}
}
