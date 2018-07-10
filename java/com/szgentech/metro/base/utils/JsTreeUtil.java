package com.szgentech.metro.base.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.szgentech.metro.model.Liattr;
import com.szgentech.metro.model.MetroCity;
import com.szgentech.metro.model.MetroLine;
import com.szgentech.metro.model.MetroLineInterval;
import com.szgentech.metro.model.MetroLineIntervalLr;
import com.szgentech.metro.vo.Jstree;
import com.szgentech.metro.vo.Jstree.State;

/**
 * 通用权限树
 * 
 * @author MAJL
 *
 */
public class JsTreeUtil {

	private static final Map<String, String> lrIcons;
	private static final Map<String, String> lrText;
	static {
		lrIcons = new HashMap<String, String>();
		lrIcons.put("l", "left");
		lrIcons.put("r", "right");

		lrText = new HashMap<String, String>();
		lrText.put("l", "左线");
		lrText.put("r", "右线");
	}

	/**
	 * 权限树json
	 * 
	 * @param request
	 * @param city
	 * @param urls
	 *            String[4] 0city 1line 2interval 3lr str
	 * @param diss
	 *            Boolean[4] 0city 1line 2interval 3lr true/false
	 * @return
	 */
	public static List<Jstree> getTreeData(HttpServletRequest request, MetroCity city, String[] urls, Boolean[] diss) {
		List<Jstree> tree = new ArrayList<Jstree>();
		Jstree itemCity = new Jstree();
		int ln = 0;
		// 组装树
		if (CommonUtils.isNotNull(city)) {
			itemCity.setId("city");
			itemCity.setIcon("city");
			itemCity.setText(city.getCityName());
			itemCity.setType("city");
			if (urls[0] != null && !"".equals(urls[0])) {
				itemCity.setUrl(request.getContextPath() + urls[0] + "?cityId=" + city.getId());
			}
			State cityState = itemCity.new State();
			cityState.setOpened(true);
			cityState.setDisabled(diss[0] != null ? diss[0] : false);
			if (!(diss[0] != null ? diss[0] : false)) {
				cityState.setSelected(true);
			}
			itemCity.setState(cityState);
			// 线路
			List<Jstree> lineList = new ArrayList<Jstree>();
			if (CommonUtils.isNotNull(city.getLineList())) {
				for (MetroLine line : city.getLineList()) {
					Jstree itemLine = new Jstree();
					itemLine.setId("line_" + line.getId());
					itemLine.setIcon("line");
					itemLine.setText(line.getLineName());
					itemLine.setType("line");
					if (urls[1] != null && !"".equals(urls[1])) {
						itemLine.setUrl(request.getContextPath() + urls[1] + "?lineId=" + line.getId());
					}
					State lineState = itemLine.new State();
					lineState.setOpened(true);
					lineState.setDisabled(diss[1] != null ? diss[1] : false);
					itemLine.setState(lineState);
					// 区间
					List<Jstree> intervalList = new ArrayList<Jstree>();
					if (CommonUtils.isNotNull(line.getIntervalList())) {
						for (MetroLineInterval interval : line.getIntervalList()) {
							Jstree itemInterval = new Jstree();
							itemInterval.setId("interval_" + interval.getId());
							itemInterval.setIcon("area");
							itemInterval.setText(interval.getIntervalName());
							itemInterval.setType("area");
							if (urls[2] != null && !"".equals(urls[2])) {
								itemInterval.setUrl(request.getContextPath() + urls[2] + "?intervalId=" + interval.getId());
							}
							State intervalState = itemInterval.new State();
							intervalState.setOpened(true);
							intervalState.setDisabled(diss[2] != null ? diss[2] : false);
							itemInterval.setState(intervalState);

							// 左右线
							List<Jstree> lr_List = new ArrayList<Jstree>();
							for (MetroLineIntervalLr lr : interval.getIntervalLrList()) {
								if (lr.getBuildStatus() == 0) continue;
								Jstree itemLr = new Jstree();
								itemLr.setId("lr_" + lr.getId() + lr.getLeftOrRight());
								String lrName = lr.getLeftOrRight();
								itemLr.setIcon(lrIcons.get(lrName));
								itemLr.setText(lrText.get(lrName));
								Liattr liattr = new Liattr();
								liattr.setLi_attr(lr.getBuildStatus());
								itemLr.setLi_attr(liattr);
								itemLr.setType("lr");
								if (urls[3] != null && !"".equals(urls[3])) {
									itemLr.setUrl(request.getContextPath() + urls[3] + "?intervalId="
											+ interval.getId() + "&leftOrRight=" + lr.getLeftOrRight());
								}
								State lrState = itemLr.new State();
								lrState.setOpened(true);
								lrState.setDisabled(diss[3] != null ? diss[3] : false);
								if (ln == 0 && diss[2] != null && diss[2] == true
										&& (diss[3] != null ? diss[3] : false) == false) {
									lrState.setSelected(true);
									ln++;
								}
								itemLr.setState(lrState);
								lr_List.add(itemLr);
							}
							if (lr_List.size() > 0) {
								itemInterval.setChildren(lr_List);
								intervalList.add(itemInterval);
							}
						}
						itemLine.setChildren(intervalList);
					}
					lineList.add(itemLine);
				}
				itemCity.setChildren(lineList);
			}
		}
		tree.add(itemCity);
		return tree;
	}

	/**
	 * 报表
	 * 
	 * @param request
	 * @param city
	 * @param urls
	 * @param diss
	 * @return
	 */
	public static List<Jstree> getTreeDataForReport(HttpServletRequest request, MetroCity city, String[] urls,
			Boolean[] diss) {
		List<Jstree> tl = new ArrayList<Jstree>();
		Jstree tree = new Jstree();
		int ln = 0;
		// 组装树
		if (CommonUtils.isNotNull(city)) {
			tree.setId("city");
			tree.setIcon("city");
			tree.setText(city.getCityName());
			tree.setType("city");
			if (urls[0] != null && !"".equals(urls[0])) {
				tree.setUrl(request.getContextPath() + urls[0] + "?cityId=" + city.getId());
			}
			State cityState = tree.new State();
			cityState.setOpened(true);
			cityState.setDisabled(diss[0] != null ? diss[0] : false);
			if ((diss[0] == null) || !diss[0]) {
				cityState.setSelected(true);
			}
			tree.setState(cityState);
			// 线路
			List<Jstree> cityChilds = new ArrayList<Jstree>();
			if (CommonUtils.isNotNull(city.getLineList())) {
				for (MetroLine line : city.getLineList()) {
					Jstree cityChild = new Jstree();
					cityChild.setId("line_" + line.getId());
					cityChild.setIcon("line");
					cityChild.setText(line.getLineName());
					cityChild.setType("line");
					if (urls[1] != null && !"".equals(urls[1])) {
						cityChild.setUrl(request.getContextPath() + urls[1] + "?lineId=" + line.getId());
					}
					State lineState = tree.new State();
					lineState.setOpened(true);
					lineState.setDisabled(diss[1] != null ? diss[1] : false);
					cityChild.setState(lineState);
					// 区间
					List<Jstree> lineChilds = new ArrayList<Jstree>();
					if (CommonUtils.isNotNull(line.getIntervalList())) {
						for (MetroLineInterval interval : line.getIntervalList()) {
							Jstree lineChild = new Jstree();
							lineChild.setId("interval_" + interval.getId());
							lineChild.setIcon("area");
							lineChild.setText(interval.getIntervalName());
							lineChild.setType("area");
							if (urls[2] != null && !"".equals(urls[2])) {
								lineChild.setUrl(request.getContextPath() + urls[2] + "?intervalId=" + interval.getId()
										+ "&lineId=" + line.getId());
							}
							State intervalState = lineChild.new State();
							intervalState.setOpened(true);
							intervalState.setDisabled(diss[2] != null ? diss[2] : false);
							if (ln == 0 && diss[2] != null && !diss[2]) {
								intervalState.setSelected(true);
								ln++;
							}
							lineChild.setState(intervalState);
							lineChilds.add(lineChild);
						}
						cityChild.setChildren(lineChilds);
					}
					cityChilds.add(cityChild);
				}
				tree.setChildren(cityChilds);
			}
		}
		tl.add(tree);
		return tl;
	}

}
