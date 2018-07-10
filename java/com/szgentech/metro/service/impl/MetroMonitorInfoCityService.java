package com.szgentech.metro.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.util.Calendar;
import com.szgentech.metro.base.page.Page;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.utils.CommonUtils;
import com.szgentech.metro.base.utils.ConfigProperties;
import com.szgentech.metro.base.utils.DateUtil;
import com.szgentech.metro.base.utils.IhistorianUtil;
import com.szgentech.metro.base.utils.MathUtil;
import com.szgentech.metro.base.utils.StringUtil;
import com.szgentech.metro.base.utils.UnitProperties;
import com.szgentech.metro.dao.IMetroDictionaryDao;
import com.szgentech.metro.dao.IMetroLineIntervalDataDao;
import com.szgentech.metro.dao.IMetroLineIntervalLrDao;
import com.szgentech.metro.dao.IMetroLineIntervalMdReDao;
import com.szgentech.metro.dao.IMetroLineIntervalSpDao;
import com.szgentech.metro.dao.IMetroMonitorCityDao;
import com.szgentech.metro.dao.IRingStatusDao;
import com.szgentech.metro.model.KeyValue;
import com.szgentech.metro.model.MetroDictionary;
import com.szgentech.metro.model.MetroLineIntervalData;
import com.szgentech.metro.model.MetroLineIntervalLr;
import com.szgentech.metro.model.MetroLineIntervalMd;
import com.szgentech.metro.model.MetroLineIntervalSp;
import com.szgentech.metro.model.MetroLineIntervalWarningRec;
import com.szgentech.metro.model.MetroLoopMark;
import com.szgentech.metro.model.MonitorInfoCity;
import com.szgentech.metro.model.MonitorInterDataViewItem;
import com.szgentech.metro.model.MonitorInterDataViewPoint;
import com.szgentech.metro.model.MonitorIntervalData;
import com.szgentech.metro.model.MonitorIntervalDataView;
import com.szgentech.metro.model.MonitorIntervalSettlementPoint;
import com.szgentech.metro.model.Pandect;
import com.szgentech.metro.model.RingStatus;
import com.szgentech.metro.service.IMetroMonitorInfoCityService;
import com.szgentech.metro.vo.IhistorianResponse;
import com.szgentech.metro.vo.MonitorIntervalLrStaticView;
import com.szgentech.metro.vo.MonitorIntervalLrStaticsView;
import com.szgentech.metro.vo.MonitorIntervalView;
import com.szgentech.metro.vo.MonitorLrAlldicView;
import com.szgentech.metro.vo.MonitorStiaticParamView;
import com.szgentech.metro.vo.MonitorViewData;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Service("infoCityService")
public class MetroMonitorInfoCityService implements IMetroMonitorInfoCityService {

	private static Logger logger = Logger.getLogger(MetroMonitorInfoCityService.class);

	private final static String[] KPSS2 = { "A0001", "A0002", "J0001", "A0003", "B0006", "B0004", "B0002", "B0001",
			"B0003", "B0015", "A0004", "A0005", "A0011", "A0007", "A0009", "A0012", "R0054", "R0055", "A0013", "C0001",
			"C0009", "C0003", "C0011", "C0005", "C0013", "C0007", "C0015", "F0006", "F0001", "F0002", "F0005", "R0026",
			"R0028", "R0025", "R0056", "R0017", "R0018", "R0003", "R0004", "R0009", "E0019", "E0008", "E0016", "E0002",
			"E0010", "E0004", "E0012", "E0006", "E0014", "D0023", "D0008", "D0016", "D0010", "D0018", "D0012", "D0020",
			"D0014", "D0022" };

	private final static String[] KPS = { "A0001", "A0002", "A0004", "B0001", "B0002", "B0004", "B0006", "D0023" };
	private final static String[] KPSS = { "A0001", "A0002", "A0003", "A0004", "A0005", "A0006", "A0007", "A0008",
			"A0009", "A0011", "B0001", "B0002", "B0004", "B0006", "B0011", "B0012", "C0001", "C0003", "C0005", "C0007",
			"C0009", "C0011", "C0013", "C0015", "D0008", "D0010", "D0012", "D0014", "D0016", "D0018", "D0020", "D0022",
			"D0023", "E0002", "E0004", "E0006", "E0008", "E0010", "E0012", "E0014", "E0016", "E0019", "F0001", "F0002",
			"F0005", "F0006", "G0002", "G0003", "G0004", "G0006", "G0007", "G0008", "G0010", "G0011", "G0012", "G0014",
			"G0015", "G0016", "G0018", "G0019", "G0020", "G0022", "G0023", "G0024", "G0026", "G0027", "G0028", "G0030",
			"G0031", "G0032", "H0042", "H0043", "H0040", "H0041", "I0020", "J0001", "J0002", "J0003", "J0004", "J0005",
			"J0006", "J0007", "J0008", "J0009", "J0010", "J0011", "J0012", "J0020", "J0021", "J0022", "J0023", "J0024",
			"J0025", "J0026", "J0027", "J0028", "J0029", "J0030", "R0001", "R0002", "R0003", "R0004", "R0005", "R0006",
			"R0007", "R0008", "R0009", "R0010", "R0011", "R0012", "R0013", "R0014", "R0015", "R0016", "R0017", "R0018",
			"R0019", "R0020", "R0021", "R0022", "R0023", "R0024", "A0013", "B0015", "R0025", "R0026", "R0028"};

	private final static String[] KPSM = { "J0001" };

	private final static String[] ILRDX = { "J0001", "J0020", "J0021", "J0022", "J0023", "J0029", "J0028" };

	private final static String[] MODEL1 = { "A0004", "B0006", "B0004", "B0001", "B0002" }; // 土压掘进参数
	private final static String[] MODEL2 = { "A4579", "B0006", "E0019" }; // (A0004+A0005+A0007+A0009+A0011) AS A4579
	private final static String[] MODEL3 = { "C0913", "C0105", "J0025", "J0027" }; // 垂直姿态 (C0009-C0013) AS C0913;
																					// (C0001-C0005) AS C0105

	private final static String[] MODEL4 = { "B0011", "B0012" };// 设备故障
	private final static String[] MODEL5 = { "C1511", "C0703", "J0024", "J0026" }; // 水平姿态 (C0015-C0011) AS C1511;
																					// (C0007-C0003) AS C0703
	private final static String[] MODEL6 = { "B0001", "H0044" };
	private final static String[] MODEL7 = { "D0023", "B0001" };
	private final static String[] MODEL8 = { "J0020", "J0021", "J0024", "J0025" };

	// 泥水盾构的数据风险“默认模式”九和十的参数
	private final static String[] MODEL9 = { "B0001", "J0021", "B0015", "B0004", "B0003" };
	private final static String[] MODEL10 = { "B0001", "R0026", "R0025", "R0028", "R0003" };

	private final static String[] MODEL11 = { "A0003", "D0023", "D0016", "B0001" };// 同步注浆
	private final static String[] MODEL12 = { "A0004", "F0001", "B0001", "B0004", "B0003" };// 土压出土管理
	private final static String[] MODEL13 = { "A0013", "B0004", "B0002", "B0006", "B0001" };// 泥水掘进参数
	private final static String[] MODEL14 = { "A0013", "R0025", "R0004" };// 环流参数1
	private final static String[] MODEL15 = { "R0026", "R0028", "B0001" };// 环流参数2

	// 泥水画面需要更新的参数

	private final static String[] SLURRY = { "A0001", "A0002", "A0003", "A0004", "A0012", "A0013", "R0001", "R0002",
			"R0003", "R0004", "R0007", "R0009", "R0015", "R0016", "R0017", "R0018", "R0025", "R0026", "R0027", "R0028",
			"R0029", "R0054", "R0055", "R0056", "R0057", "R0058", "J0001", "J0011", "J0012", "J0020", "J0021" };
	// 刀盘画面需要更新的参数
	private final static String[] KNIFE = { "A0001", "A0002", "A0003", "A0004", "A0005", "A0007", "A0009", "A0011",
			"A0012", "A0013", "B0001", "B0002", "B0004", "B0006", "B0011", "B0012", "C0001", "C0003", "C0005", "C0007",
			"C0009", "C0011", "C0013", "C0015", "D0008", "D0010", "D0012", "D0014", "D0016", "D0018", "D0020", "D0022",
			"D0023", "E0002", "E0004", "E0006", "E0008", "E0010", "E0012", "E0014", "E0016", "E0019", "J0001", "J0011",
			"J0012", "J0020", "J0021", "J0022", "J0023", "J0024", "J0025", "R0054" };
	// 螺旋画面需要更新的参数
	private final static String[] SPIRAL = { "A0001", "A0002", "A0003", "A0004", "A0005", "A0007", "A0009", "A0011",
			"B0001", "B0002", "B0004", "B0006", "F0001", "F0002", "F0003", "F0004", "F0005", "F0006", "G0002", "G0003",
			"G0004", "G0006", "G0007", "G0008", "G0010", "G0011", "G0012", "G0014", "G0015", "G0016", "G0018", "G0019",
			"G0020", "G0022", "G0023", "G0024", "G0026", "G0027", "G0028", "G0030", "G0031", "G0032", "H0001", "I0020",
			"J0001", "J0011", "J0012", "J0020", "J0021" };

	@Autowired
	private IMetroMonitorCityDao monitorCityDao;

	@Autowired
	private IMetroDictionaryDao dicDao;

	@Autowired
	private IMetroLineIntervalDataDao lineIntervalDataDao;

	@Autowired
	private IMetroLineIntervalSpDao lineIntervalSpDao;

	@Autowired
	private IMetroLineIntervalMdReDao lineIntervalMdReDao;
	
	@Autowired
	private IRingStatusDao ringStatusDao;
	
	@Autowired
	private IMetroLineIntervalLrDao lineIntervalLrDao;

	@Override
	public PageResultSet<MonitorViewData> findMonitorInfoCityData(Long cityId, Long lineId, int buildStatus,
			String intervalName, int pageNum, int pageSize, Long userId) throws IOException {
		PageResultSet<MonitorViewData> result = null;
		Map<String, Object> params = new HashMap<>();
		params.put("cityId", cityId);
		params.put("lineId", lineId);
		if (buildStatus >= 0) {
			params.put("buildStatus", buildStatus);
		}
		if (intervalName != null) {
			params.put("intervalName", intervalName);
		}
		if (userId != null) {
			params.put("userId", userId);
		}
		int total = monitorCityDao.countMonitorInfoCityDatas(params);
		Page page = new Page(total, pageSize, pageNum);
		if (total > 0) {
			params.put("start", page.getBeginIndex());
			params.put("pageSize", page.getPageSize());
			List<MonitorInfoCity> mics = monitorCityDao.findMonitorInfoCityDatas(params);
			result = new PageResultSet<>();
			List<MonitorViewData> list = new ArrayList<MonitorViewData>();
			MonitorViewData mvd = null;
			List<String> keys = new ArrayList<String>();
			for (MonitorInfoCity mic : mics) {
				mvd = new MonitorViewData();
				mvd.setIntervalName(mic.getIntervalName());
				mvd.setBuildStatus(mic.getBuildStatus());
				mvd.setTotalRingNum(mic.getRingNum());
				mvd.setLeftOrRight(mic.getLeftOrRight());
				mvd.setLineName(mic.getLineName());
				mvd.setIntervalId(mic.getIntervalId());
				mvd.setBuildDate(mic.getBuildDate());
				mvd.setThroughDate(mic.getThroughDate());
				for (String p : KPS) {
					keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), p));
				}
				list.add(mvd);
			}
			IhistorianResponse ir = IhistorianUtil.getDataByKeysRing(keys);
			if (ir != null && ir.getCode() == 200) {
				int i = 0;
				Map<String, Object> map = ir.getResult();

				for (MonitorInfoCity mic : mics) {
					keys = new ArrayList<String>();
					for (String p : KPS) {
						keys.add(
								IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), p));
					}

					Object obj = map.get(keys.get(0));
					if (obj != null) {
						String A0001 = new ObjectMapper().writeValueAsString(obj);
						Map<String, Object> resultMap = new ObjectMapper().readValue(A0001, HashMap.class);
						list.get(i).setA0001(Float.parseFloat(resultMap.get("ring").toString()));
						int quality = StringUtil.nullToInt(resultMap.get("quality").toString());
						list.get(i).setCommuniStatus(quality == 100 ? 1 : 0);
					} else {
						list.get(i).setA0001(null);
						list.get(i).setCommuniStatus(0);
					}
					obj = map.get(keys.get(1));
					list.get(i).setA0002(obj != null ? Integer.parseInt(obj.toString()) : null);
					obj = map.get(keys.get(2));
					list.get(i).setA0004(obj != null ? Float.parseFloat(obj.toString()) : null);
					obj = map.get(keys.get(3));
					list.get(i).setB0001(obj != null ? Float.parseFloat(obj.toString()) : null);
					obj = map.get(keys.get(4));
					list.get(i).setB0002(obj != null ? Float.parseFloat(obj.toString()) : null);
					obj = map.get(keys.get(5));
					list.get(i).setB0004(obj != null ? Float.parseFloat(obj.toString()) : null);
					obj = map.get(keys.get(6));
					list.get(i).setB0006(obj != null ? Float.parseFloat(obj.toString()) : null);
					obj = map.get(keys.get(7));
					list.get(i).setD0023(obj != null ? Float.parseFloat(obj.toString()) : null);
					i++;
				}
			}
			result.setPage(page);
			result.setList(list);
		}

		return result;
	}

	@Override
	public Map<String, String> findCountMechineDatas(Long userId, Long lineId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("lineId", lineId);
		List<KeyValue> list = monitorCityDao.findCountMechineDatas(params);
		Map<String, String> map = null;
		if (list != null && list.size() > 0) {
			map = new HashMap<String, String>();
			for (KeyValue k : list) {
				map.put("total" + k.getKey(), k.getValue());
			}
		}
		List<MonitorInfoCity> mics = monitorCityDao.findAllMonitorInfoCityDatas(params);
		List<String> keys = new ArrayList<String>();
		for (MonitorInfoCity mic : mics) {
			keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "A0001"));
		}
		IhistorianResponse ir = IhistorianUtil.getCalcByKeys(keys);
		if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
			map.put("totalSuccess",
					ir.getResult().get("success") != null ? ir.getResult().get("success").toString() : "0");
			map.put("totalFail", ir.getResult().get("fail") != null ? ir.getResult().get("fail").toString() : "0");
		}
		return map;
	}

	@Override
	public Map<String, Object> findIntervalLrDaoPanDatas(Long intervalId, String leftOrRight) {
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> map = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		// 从数据库中获取区间信息
		/*
		 * line_no: 线路编号
		 * line_name: 线路名称
		 * interval_name: 区间名称
		 * interval_mark: 工程标号
		 * build_status: 施工状态
		 * left_or_right: 左右线
		 * ring_num: 总环数
		 */
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			List<String> keys = new ArrayList<String>();
			for (String p : KPSS) {
				keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), p));
			}
			IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys);
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				map = ir.getResult();
				String key = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "A0001");
				Integer nowRingNum = StringUtil.nullToInt(map.get(key) != null ? map.get(key).toString() : "0");
				if (nowRingNum > 0) {
					List ringarr = new ArrayList();
					for (int i = 0; i < 10; i++) {
						if (nowRingNum - i > 0) {
							ringarr.add(nowRingNum - i);
						} else {
							break;
						}
					}
					List<String> ks = new ArrayList<String>();
					/*
					 * ks.add("GZ" + mic.getLineNo() + "_" + mic.getIntervalMark() +
					 * mic.getLeftOrRight().toUpperCase() + "." + "J0020.F_CV"); ks.add("GZ" +
					 * mic.getLineNo() + "_" + mic.getIntervalMark() +
					 * mic.getLeftOrRight().toUpperCase() + "." + "J0021.F_CV");
					 */
					ks.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "J0020"));
					ks.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "J0021"));
					IhistorianResponse irr = IhistorianUtil.getDataByKeysAndRingNums(ringarr, ks);
					if (irr != null && irr.getCode() == 200 && irr.getResult() != null) {
						List<HashMap<String, HashMap>> list = (List<HashMap<String, HashMap>>) irr.getResult().get("list");
						List<String> hs = new ArrayList<String>();
						List<String> vs = new ArrayList<String>();
						for (Map m : list) {
							Iterator i = m.keySet().iterator();
							while (i.hasNext()) {
								String o = i.next().toString();
								Map<String, Object> hv = (Map) m.get(o);
								hs.add(hv.get("h").toString());
								vs.add(hv.get("v").toString());
								break;
							}
						}
						Collections.reverse(hs);
						Collections.reverse(vs);
						map.put("ringNumH", hs);
						map.put("ringNumV", vs);
					}
				}
				Map<String, Object> front = IhistorianUtil.getMapAndMileage(map, mic, "J0002", "J0003", "J0004", "J0001");
				if (front != null) {
					double frontMin = Math.floor(StringUtil.nullToDouble(front.get("mileage").toString()));
					double frontMax = frontMin + 1;
					Map<String, Object> frontParams = new HashMap<>();
					frontParams.put("intervalId", intervalId);
					frontParams.put("leftOrRight", leftOrRight);
					frontParams.put("mileage", frontMin);
					MetroLineIntervalData frontDataMin = lineIntervalDataDao.findUniqueData(frontParams);
					frontParams.put("mileage", frontMax);
					MetroLineIntervalData frontDataMax = lineIntervalDataDao.findUniqueData(frontParams);
					if (frontDataMin != null && frontDataMax != null) {
						double hfront = 0D;
						hfront = MathUtil.getHdeviation(
								StringUtil.nullToDouble(front.get("mapX") == null ? "" : front.get("mapX").toString()),
								StringUtil.nullToDouble(front.get("mapY") == null ? "" : front.get("mapY").toString()),
								frontDataMin.getMapX().doubleValue(), frontDataMin.getMapY().doubleValue(),
								frontDataMax.getMapX().doubleValue(), frontDataMax.getMapY().doubleValue());
						if (mic.getDirection().equals("1")) {
							hfront *= -1D;
						}

						double vfront = MathUtil.getVdeviation(
								StringUtil.nullToDouble(front.get("mapZ") == null ? "" : front.get("mapZ").toString()),
								frontDataMin.getMapZ().doubleValue(), frontDataMax.getMapZ().doubleValue(),
								StringUtil.nullToDouble(front.get("mileage") == null ? "" : front.get("mileage").toString()),
								frontMin, frontMax);
						map.put("hfront", hfront);
						map.put("vfront", vfront);
					}

				}
				Map<String, Object> medium = IhistorianUtil.getMapAndMileage(map, mic, "J0005", "J0006", "J0007", "J0028");
				if (medium != null) {
					double mediumMin = Math.floor(StringUtil.nullToDouble(medium.get("mileage").toString()));
					double mediumMax = mediumMin + 1;
					Map<String, Object> mediumParams = new HashMap<>();
					mediumParams.put("intervalId", intervalId);
					mediumParams.put("leftOrRight", leftOrRight);
					mediumParams.put("mileage", mediumMin);
					MetroLineIntervalData mediumDataMin = lineIntervalDataDao.findUniqueData(mediumParams);
					mediumParams.put("mileage", mediumMax);
					MetroLineIntervalData mediumDataMax = lineIntervalDataDao.findUniqueData(mediumParams);
					if (mediumDataMin != null && mediumDataMax != null) {
						double hMedium = MathUtil.getHdeviation(
								StringUtil.nullToDouble(medium.get("mapX") == null ? "" : medium.get("mapX").toString()),
								StringUtil.nullToDouble(medium.get("mapY") == null ? "" : medium.get("mapY").toString()),
								mediumDataMin.getMapX().doubleValue(), mediumDataMin.getMapY().doubleValue(),
								mediumDataMax.getMapX().doubleValue(), mediumDataMax.getMapY().doubleValue());
						if (mic.getDirection().equals("1")) {
							hMedium *= -1D;
						}

						double vMedium = MathUtil.getVdeviation(
								StringUtil.nullToDouble(medium.get("mapZ") == null ? "" : medium.get("mapZ").toString()),
								mediumDataMin.getMapZ().doubleValue(), mediumDataMax.getMapZ().doubleValue(),
								StringUtil.nullToDouble(front.get("mileage") == null ? "" : front.get("mileage").toString()),
								mediumMin, mediumMax);
						map.put("hMedium", hMedium);
						map.put("vMedium", vMedium);
					}
				}
				Map<String, Object> back = IhistorianUtil.getMapAndMileage(map, mic, "J0008", "J0009", "J0010", "J0029");
				if (back != null) {
					double backMin = Math.floor(StringUtil.nullToDouble(back.get("mileage").toString()));
					double backMax = backMin + 1;
					Map<String, Object> backParams = new HashMap<>();
					backParams.put("intervalId", intervalId);
					backParams.put("leftOrRight", leftOrRight);
					backParams.put("mileage", backMin);
					MetroLineIntervalData backDataMin = lineIntervalDataDao.findUniqueData(backParams);
					backParams.put("mileage", backMax);
					MetroLineIntervalData backDataMax = lineIntervalDataDao.findUniqueData(backParams);
					if (backDataMin != null && backDataMax != null) {
						double hBack = MathUtil.getHdeviation(
								StringUtil.nullToDouble(back.get("mapX") == null ? "" : back.get("mapX").toString()),
								StringUtil.nullToDouble(back.get("mapY") == null ? "" : back.get("mapY").toString()),
								backDataMin.getMapX().doubleValue(), backDataMin.getMapY().doubleValue(),
								backDataMax.getMapX().doubleValue(), backDataMax.getMapY().doubleValue());
						if (mic.getDirection().equals("1")) {
							hBack *= -1D;
						}
						double vBack = MathUtil.getVdeviation(
								StringUtil.nullToDouble(back.get("mapZ") == null ? "" : back.get("mapZ").toString()),
								backDataMin.getMapZ().doubleValue(), backDataMax.getMapZ().doubleValue(),
								StringUtil.nullToDouble(front.get("mileage") == null ? "" : front.get("mileage").toString()),
								backMin, backMax);
						map.put("hBack", hBack);
						map.put("vBack", vBack);
					}

				}

			}
			map.put("lineName", mic.getLineName());
			map.put("intervalMark", mic.getIntervalMark());
			map.put("intervalName", mic.getIntervalName());
			map.put("leftOrRight", mic.getLeftOrRight());
			map.put("ringNum", mic.getRingNum());
			map.put("head", IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight()) + ".");
		}

		return map;
	}

	@Override
	public Map<String, Object> getSlurryData(Long intervalId, String leftOrRight) {
		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		// 从数据库中获取区间信息
		/*
		 * line_no: 线路编号
		 * line_name: 工程名称
		 * interval_name: 
		 * interval_mark: 工程标号
		 * build_status: 施工状态
		 * left_or_right: 左右线
		 * ring_num: 总环数
		 */
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			Integer lineNumber = mic.getLineNo();
			Integer intervalMark = mic.getIntervalMark();

			List<String> keys = new ArrayList<String>();
			for (String p : SLURRY) {
				keys.add(IhistorianUtil.getKey(lineNumber, intervalMark, leftOrRight, p));
			}
			IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys);
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				map = ir.getResult();
			}

			map.put("lineName", mic.getLineName());
			map.put("intervalMark", mic.getIntervalMark());
			map.put("intervalName", mic.getIntervalName());
			map.put("leftOrRight", mic.getLeftOrRight());
			map.put("ringNum", mic.getRingNum());
			map.put("head", IhistorianUtil.getKey(lineNumber, intervalMark, leftOrRight) + ".");
		}

		return map;
	}

	@Override
	public Map<String, Object> getKnifeData(Long intervalId, String leftOrRight) {
		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		// 从数据库中获取区间信息
		/*
		 * line_no: 线路编号
		 * line_name: 工程名称
		 * interval_name: 
		 * interval_mark: 工程标号
		 * build_status: 施工状态
		 * left_or_right: 左右线
		 * ring_num: 总环数
		 */
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			Integer lineNumber = mic.getLineNo();
			Integer intervalMark = mic.getIntervalMark();

			List<String> keys = new ArrayList<String>();
			for (String p : KNIFE) {
				keys.add(IhistorianUtil.getKey(lineNumber, intervalMark, leftOrRight, p));
			}
			IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys);
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				map = ir.getResult();
			}

			map.put("lineName", mic.getLineName());
			map.put("intervalMark", mic.getIntervalMark());
			map.put("intervalName", mic.getIntervalName());
			map.put("leftOrRight", mic.getLeftOrRight());
			map.put("ringNum", mic.getRingNum());
			map.put("head", IhistorianUtil.getKey(lineNumber, intervalMark, leftOrRight) + ".");
		}

		return map;
	}

	@Override
	public Map<String, Object> getSpiralData(Long intervalId, String leftOrRight) {
		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		// 从数据库中获取区间信息
		/*
		 * line_no: 线路编号 line_name: 工程名称 interval_name: interval_mark: 工程标号
		 * build_status: 施工状态 left_or_right: 左右线 ring_num: 总环数
		 */
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			Integer lineNumber = mic.getLineNo();
			Integer intervalMark = mic.getIntervalMark();

			List<String> keys = new ArrayList<String>();
			for (String p : SPIRAL) {
				keys.add(IhistorianUtil.getKey(lineNumber, intervalMark, leftOrRight, p));
			}
			IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys);
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				map = ir.getResult();
			}

			map.put("lineName", mic.getLineName());
			map.put("intervalMark", mic.getIntervalMark());
			map.put("intervalName", mic.getIntervalName());
			map.put("leftOrRight", mic.getLeftOrRight());
			map.put("ringNum", mic.getRingNum());
			map.put("head", IhistorianUtil.getKey(lineNumber, intervalMark, leftOrRight) + ".");
		}

		return map;
	}

	@Override
	public PageResultSet<MonitorLrAlldicView> findMonitorIntervalLrDics(long parseLong, String leftOrRight) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", parseLong);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		params.put("start", 0);
		params.put("pageSize", 500);
		List<MetroDictionary> dics = dicDao.findObjsList(params);
		PageResultSet<MonitorLrAlldicView> res = new PageResultSet<MonitorLrAlldicView>();
		if (mic != null) {
			List<String> keys = new ArrayList<String>();
			for (MetroDictionary dic : dics) {
				keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(),
						dic.getDicName()));
			}
			IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys);
			Map<String, Object> map = null;
			if (ir != null) {
				map = ir.getResult();
			}
			List<MonitorLrAlldicView> list = new ArrayList<MonitorLrAlldicView>();
			MonitorLrAlldicView mav = null;
			int step = 1;
			for (MetroDictionary dic : dics) {
				String key = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(),
						dic.getDicName());
				Object obj = map != null ? map.get(key) : null;
				if (step == 1) {
					mav = new MonitorLrAlldicView();
					mav.setKey1(dic.getDicMean());
					mav.setValue1((obj != null ? obj : "-") + dic.getDicUnit());
					step++;
				} else if (step == 2) {
					mav.setKey2(dic.getDicMean());
					mav.setValue2((obj != null ? obj : "-") + dic.getDicUnit());
					step++;
				} else if (step == 3) {
					mav.setKey3(dic.getDicMean());
					mav.setValue3((obj != null ? obj : "-") + dic.getDicUnit());
					step++;
				} else {
					mav.setKey4(dic.getDicMean());
					mav.setValue4((obj != null ? obj : "-") + dic.getDicUnit());
					list.add(mav);
					step = 1;
				}
			}
			res.setList(list);
			res.setPage(new Page(0, 100, 1));
		}
		return res;
	}

	/**
	 * APP需要去掉后缀单位
	 */
	@Override
	public PageResultSet<MonitorLrAlldicView> findMonitorIntervalLrDics2(long parseLong, String leftOrRight) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", parseLong);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		params.put("start", 0);
		params.put("pageSize", 500);
		List<MetroDictionary> dics = dicDao.findObjsList(params);
		PageResultSet<MonitorLrAlldicView> res = new PageResultSet<MonitorLrAlldicView>();
		if (mic != null) {
			List<String> keys = new ArrayList<String>();
			for (MetroDictionary dic : dics) {
				keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(),
						dic.getDicName()));
			}
			IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys);
			Map<String, Object> map = null;
			if (ir != null) {
				map = ir.getResult();
			}
			List<MonitorLrAlldicView> list = new ArrayList<MonitorLrAlldicView>();
			MonitorLrAlldicView mav = null;
			int step = 1;
			for (MetroDictionary dic : dics) {
				String key = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(),
						dic.getDicName());
				Object obj = map != null ? map.get(key) : null;
				if (step == 1) {
					mav = new MonitorLrAlldicView();
					mav.setKey1(dic.getDicMean());
					mav.setValue1((obj != null ? obj : "-") + "");
					step++;
				} else if (step == 2) {
					mav.setKey2(dic.getDicMean());
					mav.setValue2((obj != null ? obj : "-") + "");
					step++;
				} else if (step == 3) {
					mav.setKey3(dic.getDicMean());
					mav.setValue3((obj != null ? obj : "-") + "");
					step++;
				} else {
					mav.setKey4(dic.getDicMean());
					mav.setValue4((obj != null ? obj : "-") + "");
					list.add(mav);
					step = 1;
				}
			}
			res.setList(list);
			res.setPage(new Page(0, 100, 1));
		}
		return res;
	}

	@Override
	public MonitorIntervalView findMonitorIntervalDatas(Long intervalId, Long intervalSpId) {
		MonitorIntervalView miv = null;
		Map<String, Object> params = new HashMap<>();
		params.put("intervalSpId", intervalSpId);
		MetroLineIntervalSp mlis = lineIntervalSpDao.findObjById(params);
		params.put("intervalId", intervalId);
		params.put("leftOrRight", mlis.getLeftOrRight());
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		params.clear();
		if (mic != null) {
			miv = new MonitorIntervalView();
			miv.setTitle("[" + mic.getLineName() + "-" + mic.getIntervalMark() + "标-" + mic.getIntervalName() + "-"
					+ ("l".equals(mic.getLeftOrRight()) ? "左" : "右") + "线]监测数据");
			miv.setIntervalSpId(intervalSpId);
			List<String> keys = new ArrayList<String>();
			for (String k : KPSM) {
				keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), k));
			}
			IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys, mlis.getOriginMileage());
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				Map m = ir.getResult();
				Long b = Long.parseLong(m.get("30m") != null ? m.get("30m").toString() : "0");
				Long e = Long.parseLong(m.get("50m") != null ? m.get("50m").toString() : "0");
				if (b > 0 && e > 0) {
					params.clear();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					params.put("beginTime", sdf.format(new Date(b * 1000)));
					params.put("endTime", sdf.format(new Date(e * 1000)));
					params.put("mdNo", mlis.getSpName());
					List<MetroLineIntervalMd> mlims = monitorCityDao.findMonitorInfoIntervalData(params);
					List<String> times = new ArrayList<String>();
					List<String> times1 = new ArrayList<String>();
					for (MetroLineIntervalMd md : mlims) {
						times.add(String.valueOf(md.getMonitorDate().getTime() / 1000));
						times1.add(sdf.format(md.getMonitorDate()));
					}

					miv.setDataTime(times1);
					IhistorianResponse irr = IhistorianUtil.getDataByKeys(keys, times);
					if (irr != null && irr.getCode() == 200 && irr != null) {
						List<HashMap> list = (List<HashMap>) irr.getResult().get("list");
						if (list != null && list.size() > 0) {
							List<List> grandSettlement = new ArrayList<List>();
							List<List> speedSettlement = new ArrayList<List>();
							List vl = null;
							List vl1 = null;
							for (Map mp : list) {
								vl = new ArrayList();
								vl1 = new ArrayList();
								Object mile = mp.get(keys.get(0));
								vl.add((mile != null ? Float.parseFloat(mile.toString()) : 0)
										- mlis.getOriginMileage());
								vl1.add((mile != null ? Float.parseFloat(mile.toString()) : 0)
										- mlis.getOriginMileage());
								grandSettlement.add(vl);
								speedSettlement.add(vl1);
							}

							int i = 0;
							for (MetroLineIntervalMd md : mlims) {
								grandSettlement.get(i).add(md.getSumVar());
								speedSettlement.get(i).add(md.getSpSpeed());
								i++;
							}
							miv.setGrandSettlement(grandSettlement);
							miv.setSpeedSettlement(speedSettlement);

						}
					}
				}
			}

		}
		return miv;
	}

	@Override
	public MonitorIntervalDataView findIntervalMonitorData(Long intervalId, String date, String leftOrRight) {
		MonitorIntervalDataView monitorIntervalDataView = null;
		Map<String, Object> params = new HashMap<>(); // 查询参数
		params.put("intervalId", intervalId);
		params.put("date", date);
		params.put("leftOrRight", leftOrRight);
		// 按日期查询当天的最新监测日期
		Integer dayTheLatestMonitorDate = lineIntervalMdReDao.findDayTheLatestMonitorDate(params);
		System.out.println("dayTheLatestMonitorDate=" + dayTheLatestMonitorDate);

		if (dayTheLatestMonitorDate != null && dayTheLatestMonitorDate > 0) {
			// 区间左右线信息
			MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
			if (mic != null) {
				monitorIntervalDataView = new MonitorIntervalDataView();
				monitorIntervalDataView.setTitle("[" + mic.getLineName() + "-" + mic.getIntervalMark() + "标-"
						+ mic.getIntervalName() + "-" + ("l".equals(leftOrRight) ? "左" : "右") + "线]监测数据");

				List<String> keys = new ArrayList<String>();
				for (String k : KPSM) {
					keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), k));
				}
				List<String> times = new ArrayList<String>();
				times.add(String.valueOf(dayTheLatestMonitorDate));
				// 按时间查询盾首里程
				IhistorianResponse irr = IhistorianUtil.getDataByKeys(keys, times);
				if (irr != null && irr.getCode() == 200) {
					List<HashMap> list = (List<HashMap>) irr.getResult().get("list");
					if (list != null && list.size() > 0) {
						Map mp = list.get(0);
						Object mile = mp.get(keys.get(0));
						float message = mile != null ? Float.parseFloat(mile.toString()) : 0;
						float beginMessage = message - 50; // 开始里程
						float endMessage = message + 30; //// 结束里程
						// System.out.println("message=" + message);
						params.put("beginMessage", beginMessage);
						params.put("endMessage", endMessage);
						// 查询沉降点检测数据
						List<MonitorIntervalData> monitorIntervalDataList = monitorCityDao
								.findMonitorIntervalMonitorData(params);
						// 多个沉降点在同一曲线
						List<MonitorIntervalSettlementPoint> settlementPointList = monitorCityDao
								.findMonitorIntervalMonitorDataPoint(params);

						// 一个沉降点一条曲线
						if (monitorIntervalDataList != null && monitorIntervalDataList.size() > 0) {
							List<MonitorInterDataViewItem> dataViewItems = new ArrayList<>();
							List<String> dataTime = null;
							List<List> grandSettlement = null;
							List<List> speedSettlement = null;
							for (MonitorIntervalData data : monitorIntervalDataList) {
								MonitorInterDataViewItem dataViewItem = new MonitorInterDataViewItem();
								dataViewItem.setSpName(data.getSpName());
								List<MonitorIntervalSettlementPoint> dataPoints = data.getDataPoints();

								if (dataPoints != null && dataPoints.size() > 0) {
									dataTime = new ArrayList<>(); // //监测日期
									grandSettlement = new ArrayList<>();// [[-14,-3],[-13,3.0],[-12,5.0]] 累计沉降值
									speedSettlement = new ArrayList<>();// [[-14,-3],[-13,3.0],[-12,5.0]] 沉降速率

									List grand = null; // 累计沉降值元素
									List speed = null; // 沉降速率元素
									// for (MonitorIntervalSettlementPoint dataPoint : dataPoints) {
									for (int i = 0; i < dataPoints.size(); i++) {
										MonitorIntervalSettlementPoint dataPoint = dataPoints.get(i);
										// 监测日期
										dataTime.add(dataPoint.getMonitorDate());
										float y = message - dataPoint.getOriginMileage();
										grand = new ArrayList<>();
										speed = new ArrayList<>();

										grand.add(y);
										grand.add(dataPoint.getSumVar());

										speed.add(y);
										// 沉降速率
										float spSpeed = 0;
										if (i == 0) {
											spSpeed = dataPoint.getThisVar();
										} else {
											MonitorIntervalSettlementPoint prePoint = dataPoints.get(i - 1);
											SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
											try {
												Date currentDate = sdf.parse(dataPoint.getMonitorDate()); // 当前记录监测时间
												Date preDate = sdf.parse(prePoint.getMonitorDate());// 上一条记录监测时间

												float timesTampDiff = (currentDate.getTime() - preDate.getTime()) / 1000
														/ 60 / 60 / 24f; // 上下两条记录的时间差
												if (timesTampDiff == 0) {
													spSpeed = dataPoint.getThisVar();
												} else {
													spSpeed = dataPoint.getThisVar() / timesTampDiff;
												}
											} catch (ParseException e) {
												spSpeed = dataPoint.getThisVar();
												e.printStackTrace();
												logger.error(e.getMessage());
											}
										}
										speed.add(spSpeed);

										grandSettlement.add(grand);
										speedSettlement.add(speed);
									}
									// }
								}

								dataViewItem.setDataTime(dataTime);
								dataViewItem.setGrandSettlement(grandSettlement);
								dataViewItem.setSpeedSettlement(speedSettlement);
								dataViewItems.add(dataViewItem);
							}
							monitorIntervalDataView.setDataViewItems(dataViewItems);
						}

						// 多个沉降点在同一曲线
						if (settlementPointList != null && settlementPointList.size() > 0) {
							MonitorInterDataViewPoint dataViewPoint = new MonitorInterDataViewPoint();
							List<String> spNameList = new ArrayList<>();// 沉降点名称
							List<String> dataTime = new ArrayList<>(); // //监测日期
							List<List> grandSettlement = new ArrayList<>();// [[-14,-3],[-13,3.0],[-12,5.0]] 累计沉降值
							List<List> speedSettlement = new ArrayList<>();// [[-14,-3],[-13,3.0],[-12,5.0]] 沉降速率

							List grand = null; // 累计沉降值元素
							List speed = null; // 沉降速率元素
							for (MonitorIntervalSettlementPoint dataPoint : settlementPointList) {
								spNameList.add(dataPoint.getSpName());
								dataTime.add(dataPoint.getMonitorDate());
								float y = message - dataPoint.getOriginMileage();
								grand = new ArrayList<>();
								speed = new ArrayList<>();

								grand.add(y);
								grand.add(dataPoint.getSumVar());

								speed.add(y);
								speed.add(dataPoint.getSpSpeed());

								grandSettlement.add(grand);
								speedSettlement.add(speed);
							}
							dataViewPoint.setSpNameList(spNameList);
							dataViewPoint.setDataTime(dataTime);
							dataViewPoint.setGrandSettlement(grandSettlement);
							dataViewPoint.setSpeedSettlement(speedSettlement);
							monitorIntervalDataView.setDataViewPoint(dataViewPoint);
						}

					}
				}

			}

		}

		return monitorIntervalDataView;
	}

	@Override
	public Map<String, Object> findIntervalLrDaoxDatas(Long intervalId, String leftOrRight) {
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			List<String> keys = new ArrayList<String>();
			for (String k : ILRDX) {
				keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), k));
			}
			IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys);

			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				params = ir.getResult();
				int c = 0;
				Object[] p1 = new Object[2];
				Object[] p2 = new Object[2];
				Object[] b1 = new Object[2];
				Object[] b2 = new Object[2];

				for (String key : keys) {
					if (c == 0) {// 盾首里程
						b2[0] = params.get(key);
						p2[1] = params.get(key);
					} else if (c == 1) {// 前水平偏差
						result.put("x1", params.get(key));
						p2[0] = params.get(key);
					} else if (c == 2) {// 前垂直偏差
						result.put("y1", params.get(key));
						b2[1] = params.get(key);
					} else if (c == 3) {// 中水平偏差
						result.put("x2", params.get(key));
						p1[0] = params.get(key);
					} else if (c == 4) {// 中垂直偏差
						result.put("y2", params.get(key));
						b1[1] = params.get(key);
					} else {// 盾中里程
						b1[0] = params.get(key);
						p1[1] = params.get(key);
					}
					c++;
				}
				List p = new ArrayList();
				p.add(p1);
				p.add(p2);
				List b = new ArrayList();
				b.add(b1);
				b.add(b2);
				result.put("pyl", p);
				result.put("pylb", b);
			}
		}
		return result;
	}

	@Override
	public List<List<Object>> findMonitorStaticTab1(String intervalId, String leftOrRight, String beginRing,
			String endRing, String paramName) {
		List<List<Object>> res = new ArrayList<List<Object>>();
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			List<String> keys = new ArrayList<String>();
			keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), paramName));
			List<String> rings = new ArrayList<String>();
			List<Object> re = new ArrayList<Object>();
			for (int i = Integer.parseInt(beginRing); i <= Integer.parseInt(endRing); i++) {
				re.add(i);
				rings.add(String.valueOf(i));
			}
			res.add(re);
			IhistorianResponse ir = IhistorianUtil.getDataByKeysAndRings(keys, rings);
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				List<HashMap<String, HashMap>> result = (List<HashMap<String, HashMap>>) ir.getResult().get("list");
				List<Object> re1 = new ArrayList<Object>();
				int i = 0;
				for (String r : rings) {
					Map m = result.get(i);
					Map<String, HashMap> m1 = (Map<String, HashMap>) m.get(r);
					Object obj = m1.get(keys.get(0));
					re1.add(obj);
					i++;
				}
				res.add(re1);
			}
		}
		return res;
	}

	@Override
	public List<List<Object>> findMonitorStaticTab2(String intervalId, String leftOrRight, String beginRing,
			String endRing, String type) {
		List<List<Object>> res = new ArrayList<List<Object>>();
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		String qryParams = "A0002";
		if ("washing".equals(type)) {
			qryParams = "B0001";
		} else if ("loop".equals(type)) {
			qryParams = "R0058";
		}
		if (mic != null) {
			String key = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), qryParams);
			List<String> rings = new ArrayList<String>();
			List<Object> list1 = new ArrayList<Object>();
			for (int i = Integer.parseInt(beginRing); i <= Integer.parseInt(endRing); i++) {
				list1.add(i);
				rings.add(String.valueOf(i));
			}
			res.add(list1);
			IhistorianResponse ir = IhistorianUtil.getDataByKeyAndRings(key, rings);
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				List<HashMap<String, HashMap>> result = (List<HashMap<String, HashMap>>) ir.getResult().get("list");
				List<Object> list2 = new ArrayList<Object>();
				List<Object> list3 = new ArrayList<Object>();
				List<Object> list4 = new ArrayList<Object>();
				List<Object> list5 = new ArrayList<Object>();
				List<Object> list6 = new ArrayList<Object>();
				int i = 0;
				for (Map map : result) {
					Map<String, HashMap> m = (Map<String, HashMap>) map.get(rings.get(i));
					list2.add(m.get("K0001"));// 停止时间
					list3.add(m.get("K0002"));// 推进时间
					list4.add(m.get("K0003"));// 拼装时间
					i++;
				}

				float v1 = 0;
				for (Object f : list3) {
					v1 = v1 + Float.parseFloat(f.toString());
				}
				list5.add(v1);
				Map<String, Object> t1 = new HashMap<String, Object>();
				t1.put("value", v1);
				t1.put("name", "推进时间");

				float v2 = 0;
				for (Object f : list4) {
					v2 = v2 + Float.parseFloat(f.toString());
				}
				list5.add(v2);
				Map<String, Object> t2 = new HashMap<String, Object>();
				t2.put("value", v2);
				t2.put("name", "拼装时间");

				float v3 = 0;
				for (Object f : list2) {
					v3 = v3 + Float.parseFloat(f.toString());
				}
				list5.add(v3);
				Map<String, Object> t3 = new HashMap<String, Object>();
				t3.put("value", v3);
				t3.put("name", "停止时间");

				list6.add(t1);
				list6.add(t2);
				list6.add(t3);

				res.add(list3);
				res.add(list4);
				res.add(list2);
				res.add(list5);
				res.add(list6);
			}

		}
		return res;
	}

	@Override
	public List<List<Object>> findMonitorStaticTab3(String intervalId, String leftOrRight, Date beginDate,
			Date endDate) {
		List<List<Object>> res = new ArrayList<List<Object>>();
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			String key = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "A0001");
			List<Object> times = new ArrayList<Object>();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar beginCal = Calendar.getInstance();
			beginCal.setTime(beginDate);
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(endDate);
			long now = 0l;
			while (now <= endCal.getTimeInMillis()) {
				times.add(sdf.format(beginCal.getTime()));
				now = beginCal.getTimeInMillis();
				beginCal.add(Calendar.DATE, 1);
			}
			res.add(times);
			IhistorianResponse ir = IhistorianUtil.getDataByKeyAndTime(key, beginDate, endDate);
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				res.add((List<Object>) ir.getResult().get("list"));
			}

		}
		return res;
	}

	@Override
	public PageResultSet<MonitorIntervalLrStaticView> findMonitorStaticTab4(Long intervalId, String leftOrRight,
			int pageNum, int pageSize, Date beginTime, Date endTime, String excelType) {
		PageResultSet<MonitorIntervalLrStaticView> res = new PageResultSet<MonitorIntervalLrStaticView>();
		List<MonitorIntervalLrStaticView> result = new ArrayList<MonitorIntervalLrStaticView>();
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			String timerequest = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(),
					"A0002");
			String key1 = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "D0023");
			String key2 = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "G0001");
			List<String> keys = new ArrayList<String>();
			keys.add(key1);
			keys.add(key2);
			try {
				List<MonitorIntervalLrStaticView> msvs = new ArrayList<MonitorIntervalLrStaticView>();
				IhistorianResponse ir = IhistorianUtil.getDataByKeyAndBeTime(beginTime, endTime, timerequest, keys);
				if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
					List<HashMap<String, HashMap>> data = (List<HashMap<String, HashMap>>) ir.getResult().get("list");
					MonitorIntervalLrStaticView msv = null;
					for (Map map : data) {
						msv = new MonitorIntervalLrStaticView();
						Iterator i = map.keySet().iterator();
						while (i.hasNext()) {
							String o = i.next().toString();
							Long date = Long.parseLong(o) * 1000;
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							msv.setDate(sdf.format(new Date(date)));
							Map<String, Object> v = (Map) map.get(o);
							msv.setK0001(Float.parseFloat(v.get("K0001").toString()));
							msv.setK0002(Float.parseFloat(v.get("K0002").toString()));
							msv.setK0003(Float.parseFloat(v.get("K0003").toString()));
							if (v.containsKey(key1)) {
								msv.setD0023(Float.parseFloat(v.get(key1).toString()));
							}
							if (v.containsKey(key2)) {
								msv.setG0001(
										Float.parseFloat(v.get(key2).toString() == null ? "" : v.get(key2).toString()));
							}
							break;
						}
						msvs.add(msv);
					}
				}
				String key = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(),
						"A0001");
				IhistorianResponse irr = IhistorianUtil.getDataByKeyAndTime(key, beginTime, endTime);
				if (irr != null && irr.getCode() == 200 && irr.getResult() != null) {
					int i = 0;
					List<Object> l = (List<Object>) irr.getResult().get("list");
					for (Object o : l) {
						msvs.get(i).setRingNum(Float.parseFloat((o.toString())));
						i++;
					}
				}
				List<String> times = new ArrayList<String>();
				Calendar cal = Calendar.getInstance();
				cal.setTime(beginTime);
				long end = endTime.getTime();
				while (cal.getTime().getTime() <= end) {
					times.add(String.valueOf(cal.getTime().getTime() / 1000));
					cal.add(Calendar.DATE, 1);
				}
				keys.clear();
				keys.add(key);
				IhistorianResponse irrr = IhistorianUtil.getDataByKeys(keys, times);
				if (irr != null && irr.getCode() == 200 && irr.getResult() != null) {
					List<HashMap> ll = (List<HashMap>) irrr.getResult().get("list");
					int i = 0;
					for (Map m : ll) {
						Float ringn = m.get(key) != null ? Float.parseFloat((m.get(key).toString())) : 0;
						msvs.get(i).setBeginRing(ringn);
						msvs.get(i).setEndRing(ringn + msvs.get(i).getRingNum());
						i++;
					}
				}
				result = getMonitorStaticTab4Rec(msvs, excelType, mic);
				Page page = new Page(result.size(), pageSize, pageNum);
				res.setPage(page);
				res.setList(result);
			} catch (Exception ec) {
				ec.printStackTrace();
				logger.error(ec.getMessage());
			}
		}
		return res;
	}

	private static List<MonitorIntervalLrStaticView> getMonitorStaticTab4Rec(List<MonitorIntervalLrStaticView> data,
			String excelType, MonitorInfoCity mic) {
		List<MonitorIntervalLrStaticView> result = new ArrayList<MonitorIntervalLrStaticView>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			MonitorIntervalLrStaticView m = null;
			Float ringNum = 0f;// 推进环数
			Float K0001 = 0f;// 推进时间
			Float K0002 = 0f;// 拼装时间
			Float K0003 = 0f;// 停止时间
			Float D0023 = 0f;// 同步注浆量
			Float G0001 = 0f;// 盾尾油脂量
			if ("1".equals(excelType)) {// 日
				return data;
			} else if ("2".equals(excelType)) {// 周
				Calendar cal = Calendar.getInstance();
				int week = 0, day = 0, i = 0;
				for (MonitorIntervalLrStaticView mv : data) {
					ringNum = ringNum + mv.getRingNum();
					K0001 = K0001 + mv.getK0001();
					K0002 = K0002 + mv.getK0002();
					K0003 = K0003 + mv.getK0003();
					D0023 = mv.getD0023() != null ? D0023 + mv.getD0023() : D0023;
					G0001 = mv.getG0001() != null ? G0001 + mv.getG0001() : G0001;

					Date d1 = sdf.parse(mv.getDate());
					cal.setTime(d1);
					week = cal.get(Calendar.WEEK_OF_YEAR);
					day = cal.get(Calendar.DAY_OF_WEEK);
					if (i == 0 || day == 2) {// 记录第一和每周第一
						m = new MonitorIntervalLrStaticView();
						m.setLineMark(mic.getLineNo() + "号线-" + mic.getIntervalName() + "-"
								+ ("L".equals(mic.getLeftOrRight().toUpperCase()) ? "左" : "右") + "线");
						m.setDate(week + "周(" + sdf.format(d1) + "至");
						m.setBeginRing(mv.getBeginRing());
						m.setBeginDate(sdf.format(d1));
					}

					if (i == (data.size() - 1) || day == 1) {// 记录最后和每周最后
						m.setDate(m.getDate() + sdf.format(d1) + ")");
						m.setEndDate(sdf.format(d1));
						m.setEndRing(mv.getEndRing());
						m.setRingNum(ringNum);
						m.setK0001(K0001);
						m.setK0002(K0002);
						m.setK0003(K0003);
						m.setD0023(D0023);
						m.setG0001(G0001);
						result.add(m);
						ringNum = 0f;
						K0001 = 0f;
						K0002 = 0f;
						K0003 = 0f;
						D0023 = 0f;
						G0001 = 0f;
					}
					i++;
				}
			} else {// 月
				Calendar cal = Calendar.getInstance();
				int month = 0, day = 0, i = 0;
				for (MonitorIntervalLrStaticView mv : data) {
					ringNum = ringNum + mv.getRingNum();
					K0001 = K0001 + mv.getK0001();
					K0002 = K0002 + mv.getK0002();
					K0003 = K0003 + mv.getK0003();
					D0023 = mv.getD0023() != null ? D0023 + mv.getD0023() : G0001;
					G0001 = mv.getG0001() != null ? G0001 + mv.getG0001() : G0001;

					Date d1 = sdf.parse(mv.getDate());
					cal.setTime(d1);
					month = cal.get(Calendar.MONTH);
					day = cal.get(Calendar.DAY_OF_MONTH);
					if (i == 0 || day == 1) {// 记录第一和每周第一
						m = new MonitorIntervalLrStaticView();
						m.setLineMark(mic.getLineNo() + "号线-" + mic.getIntervalName() + "-"
								+ ("L".equals(mic.getLeftOrRight().toUpperCase()) ? "左" : "右") + "线");
						m.setDate(month + "月(" + sdf.format(d1) + "至");
						m.setBeginRing(mv.getBeginRing());
						m.setBeginDate(sdf.format(d1));
					}
					cal.add(Calendar.DATE, 1);
					day = cal.get(Calendar.DAY_OF_MONTH);
					if (i == (data.size() - 1) || day == 1) {// 记录最后和每周最后
						m.setDate(m.getDate() + sdf.format(d1) + ")");
						m.setEndDate(sdf.format(d1));
						m.setEndRing(mv.getEndRing());
						m.setRingNum(ringNum);
						m.setK0001(K0001);
						m.setK0002(K0002);
						m.setK0003(K0003);
						m.setD0023(D0023);
						m.setG0001(G0001);
						result.add(m);
						ringNum = 0f;
						K0001 = 0f;
						K0002 = 0f;
						K0003 = 0f;
						D0023 = 0f;
						G0001 = 0f;
					}
					i++;
				}
			}

		} catch (ParseException e1) {
			logger.error(e1.getMessage());
		}
		return result;
	}

	@Override
	public MonitorIntervalLrStaticsView findMonitorStaticTab5(String intervalId, String leftOrRight, int model,
			String type, Integer dataModel, Integer dataType, Date beginTime, Date endTime, int beginRing, int endRing,
			String[] ks, String[] kns, String[] indxs) {
		MonitorIntervalLrStaticsView misv = new MonitorIntervalLrStaticsView();
		String ringAPIStop = ConfigProperties.getValueByKey("RING_API_STOP");
		if(!"t".equals(type)&&"1".equals(ringAPIStop)){
			type = "t";
			Map<String, Date> timeMap = changeRingNoToTime(intervalId, leftOrRight, beginRing, endRing);
			if(timeMap == null||timeMap.get("beginTime") == null||timeMap.get("endTime") == null){
				return misv;
			}
			beginTime = timeMap.get("beginTime");
			endTime = timeMap.get("endTime");
		}
		
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			List<String> keys = new ArrayList<String>();
			String[] pns = {};
			String[] pnames = {};
			String[] inxs = {};
			if (model == 1) {// 土压掘进参数
				pns = MODEL1;
				pnames = "土压（上）,总推力,扭矩,掘进速度,刀盘转速".split(",");
				inxs = "0,1,2,3,4".split(",");
			} else if (model == 3) {// 垂直姿态
				pns = MODEL3;
				pnames = "千斤顶油压差（上下）,千斤顶行程差（上下）,盾尾垂直偏差,垂直趋势".split(",");
				inxs = "0,1,1,2".split(",");
			} else if (model == 4) {// 设备故障
				pns = MODEL4;
				pnames = "液压油箱油温,齿轮油油温".split(",");
				inxs = "0,1".split(",");
			} else if (model == 5) {// 水平姿态
				pns = MODEL5;
				pnames = "千斤顶油压差（左右）,千斤顶行程差（左右）,盾尾水平偏差,水平趋势".split(",");
				inxs = "0,1,1,2".split(",");
			} else if (model == 2) {
				pns = MODEL2;
				pnames = "土压力,总推力,铰接压力".split(",");
				inxs = "0,1,2".split(",");
			} else if (model == 6) {
				pns = MODEL6;
				pnames = "掘进速度,发泡剂注入流量".split(",");
				inxs = "0,1".split(",");
			} else if (model == 7) {
				pns = MODEL7;
				pnames = "注浆量,掘进速度".split(",");
				inxs = "0,1".split(",");
			} else if (model == 8) {
				pns = MODEL8;
				pnames = "前盾水平偏差,前盾垂直偏差,尾盾水平偏差,尾盾垂直偏差".split(",");
				inxs = "0,0,1,1".split(",");
			} else if (model == 9) {
				pns = MODEL9;
				pnames = "推进速度,切口压力(上),出土量,刀盘扭矩,刀盘贯入度".split(",");
				inxs = "0,1,2,3,4".split(",");
			} else if (model == 10) {
				pns = MODEL10;
				pnames = "推进速度,进浆流量,进浆压力,排浆流量,排浆泵进口压力".split(",");
				inxs = "0,1,2,3,4".split(",");
			} else if (model == 11) {// 同步注浆
				pns = MODEL11;
				pnames = "千斤顶行程值,同步注浆总量,同步注浆压力(1),掘进速度".split(",");
				inxs = "0,1,2,3".split(",");
			} else if (model == 12) {// 土压出土管理
				pns = MODEL12;
				pnames = "土压,螺旋机转速,掘进速度,刀盘扭矩,贯入度".split(",");
				inxs = "0,1,2,3,4".split(",");
			} else if (model == 13) {// 泥水掘进参数
				pns = MODEL13;
				pnames = "切口水压(上),刀盘扭矩,刀盘转速,总推力,掘进速度".split(",");
				inxs = "0,1,2,3,4".split(",");
			} else if (model == 14) {// 环流参数1
				pns = MODEL14;
				pnames = "切口水压(上),进浆管道压力,排浆泵P2.1出泥口压力".split(",");
				inxs = "0,1,2".split(",");
			} else if (model == 15) {// 环流参数2
				pns = MODEL15;
				pnames = "进浆流量,排浆流量,掘进速度".split(",");
				inxs = "0,1,2".split(",");
			} else {
				pns = ks;
				pnames = kns;
				inxs = indxs;
			}
			for (String paramName : pns) {
				keys.add(
						IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), paramName));
			}
			String KEY_A0001 = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(),
					"A0001");
			keys.add(KEY_A0001);
			List<String> pnamesl = new ArrayList<String>();
			Collections.addAll(pnamesl, pnames);
			misv.setpNames(pnamesl);

			IhistorianResponse ir = null;
			String key_head = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), leftOrRight);
			if ("t".equals(type)) {
				if (model > 8) {
					model = 0;
				}
				if (dataModel == 100) {
					ir = IhistorianUtil.getDataByKeysAndBeTime(keys, beginTime, endTime, model, key_head);
				} else {
					ir = IhistorianUtil.getDataByKeysAndBeTime1(keys, beginTime, endTime, model, key_head, dataModel,
							dataType);
				}
			} else {
				List<String> rings = new ArrayList<String>();
				for (int i = beginRing; i <= endRing; i++) {
					rings.add(String.valueOf(i));
				}
				if (model > 8) {
					model = 0;
				}
				if (dataModel == 100) {
					ir = IhistorianUtil.getDataByKeysAndBeRing(keys, rings, model, key_head);
				} else {
					ir = IhistorianUtil.getDataByKeysAndBeRing1(keys, rings, model, key_head, dataModel, dataType);
				}
			}
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				List<HashMap<String, HashMap>> list = (List<HashMap<String, HashMap>>) ir.getResult().get("list");
				List<String> trKeys = new ArrayList<String>();
				List<MonitorStiaticParamView> values = new ArrayList<MonitorStiaticParamView>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (Map map : list) {
					Iterator i = map.keySet().iterator();
					while (i.hasNext()) {
						String k = i.next().toString();
						Date d = new Date(Long.parseLong(k) * 1000);
						Map<String, Object> iv = (Map) map.get(k);
						String ringNum = String.valueOf(iv.get(KEY_A0001));
						if (dataModel == 100) {
							trKeys.add(sdf.format(d) + " " + ringNum);
						} else {
							trKeys.add(ringNum);
						}
						break;
					}
				}
				int keysSize = keys.size() - 1;
				for (int j = 0; j < keysSize; j++) {
					MonitorStiaticParamView mspv = new MonitorStiaticParamView();
					mspv.setName(pnamesl.get(j));
					mspv.setyAxisIndex(Integer.parseInt(inxs[j]));
					List<Object> data = new ArrayList<Object>();
					for (Map m : list) {
						Iterator ii = m.keySet().iterator();
						while (ii.hasNext()) {
							Map<String, Object> vv = (Map) m.get(ii.next());
							data.add(vv.get(keys.get(j)));
							break;
						}
					}
					mspv.setData(data);
					values.add(mspv);
				}
				misv.setKeys(trKeys);
				misv.setValues(values);
			}
		}
		return misv;
	}

	/**
	 * 获取当前环
	 */
	@Override
	public int findCurrRingNum(Long intervalId, String leftOrRight) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			List<String> keys = new ArrayList<String>();
			keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "A0001"));
			IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys);
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				try {
					return Integer.parseInt(ir.getResult().get(keys.get(0)).toString());
				} catch (Exception e) {
					logger.error("获取当前环"+e.getMessage());
					return 0;
				}
			}
		}
		return 0;
	}

	@Override
	public List<Pandect> findPandect(String lineName, String intervalName, String leftOrRight) throws IOException {
		Map<String, Object> params = new HashMap<>();
		List<Pandect> list = new ArrayList<Pandect>();
		Pandect mvd = new Pandect();
		int i = 0;
		params.put("lineName", lineName);
		params.put("intervalName", intervalName);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mics = monitorCityDao.findPandectDao(params);
		// 区间左右线不为空的时候才进入下面（自己构造7天时间）
		if (params != null) {
			String qq = "00:00:00";
			String ww = "23:59:59";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date2 = new Date();
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -6);
			date = calendar.getTime();
			String aa = dateFormat.format(date2);
			String bb = dateFormat.format(date);
			try {
				// Date转String （查询所需的时间类型条件）
				Date datea = dateFormat.parse(aa);
				Date dateb = dateFormat.parse(bb);
				// 根据时间查询推进环数
				String key = IhistorianUtil.getKey(mics.getLineNo(), mics.getIntervalMark(), mics.getLeftOrRight(),
						"A0001");
				IhistorianResponse irr = IhistorianUtil.getDataByKeyAndTime(key, dateb, datea);
				if (irr != null && irr.getCode() == 200 && irr.getResult() != null) {
					List<Object> l = (List<Object>) irr.getResult().get("list");
					mvd.setRingNum(l);
				}
			} catch (ParseException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			String starttime = aa + " " + ww;
			String endtime = bb + " " + qq;
			params.put("starttime", starttime);
			params.put("endtime", endtime);
			// 查询报警上下限
			List<MetroLineIntervalWarningRec> intervalWarningRecs = monitorCityDao.findWarningLevel(params);
			int a = 0;// 红色上限
			int b = 0;// 橙色上限
			int c = 0;// 橙色下限
			int d = 0;// 红色下限
			if (intervalWarningRecs != null && intervalWarningRecs.size()>0   &&  intervalWarningRecs.get(0) != null  ) {
				for (MetroLineIntervalWarningRec mWc : intervalWarningRecs) {
					if (mWc.getWarningLevel() == 1) {
						a++;
					}
					if (mWc.getWarningLevel() == 2) {
						b++;
					}
					if (mWc.getWarningLevel() == 3) {
						c++;
					}
					if (mWc.getWarningLevel() == 4) {
						d++;
					}
				}
				mvd.setRednessUpper(a);
				mvd.setRednessMinimum(d);
				mvd.setOrangeUpper(b);
				mvd.setOrangeMinimuml(c);
			}
		}
		mvd.setTotalRingNum(mics.getRingNum());
		// 获取当前的时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date2 = new Date();
		String asd = dateFormat.format(date2);
		try {
			// 把String转成Date
			Date date = (Date) dateFormat.parse(asd);
			System.out.println(date);
			mvd.setUpdateTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		List<String> keys = new ArrayList<String>();
		for (String p : KPS) {
			keys.add(IhistorianUtil.getKey(mics.getLineNo(), mics.getIntervalMark(), mics.getLeftOrRight(), p));
		}
		list.add(mvd);
		// 获取当前环和施工状态
		IhistorianResponse ir = IhistorianUtil.getDataByKeysRing(keys);

		if (ir != null && ir.getCode() == 200) {
			Map<String, Object> map = ir.getResult();

			keys = new ArrayList<String>();
			for (String p : KPS) {
				keys.add(IhistorianUtil.getKey(mics.getLineNo(), mics.getIntervalMark(), mics.getLeftOrRight(), p));
			}

			Object obj = map.get(keys.get(0));
			if (obj != null) {
				String A0001 = new ObjectMapper().writeValueAsString(obj);
				Map<String, Object> resultMap = new ObjectMapper().readValue(A0001, HashMap.class);
				list.get(i).setA0001(Float.parseFloat(resultMap.get("ring").toString()));
			} else {
				list.get(i).setA0001(null);
			}
			obj = map.get(keys.get(1));
			list.get(i).setA0002(obj != null ? Integer.parseInt(obj.toString()) : null);
		}
		// Float转换成integer
		if (mvd.getA0001() != null) {
			if (mvd.getA0001() > 0) {
				list.get(i).setTotal(Integer.valueOf((Math.round(mvd.getA0001()))) - 1);
			} else {
				list.get(i).setTotal(Integer.valueOf((Math.round(mvd.getA0001()))));
			}
		} else {
			list.get(i).setTotal(0);
		}
		String lRingNum = "";
		String rRingNum = "";
		// 获取今日施工环数
		IhistorianResponse ir2 = IhistorianUtil
				.getTodayRing(IhistorianUtil.getKey(mics.getLineNo(), mics.getIntervalMark(), ""));
		if (ir2 != null && ir2.getCode() == 200) {
			Map<String, Object> result1 = ir2.getResult();
			if (leftOrRight.equals("l")) {
				// 获取左线进入施工环数
				lRingNum = result1.get("L") == null ? null
						: String.valueOf(((Map<?, ?>) result1.get("L")).get("count"));
				list.get(i).setToday(lRingNum != null ? Integer.parseInt(lRingNum.toString()) : null);
			} else {
				// 获取右线进入施工环数
				rRingNum = result1.get("R") == null ? null
						: String.valueOf(((Map<?, ?>) result1.get("R")).get("count"));
				list.get(i).setToday(rRingNum != null ? Integer.parseInt(rRingNum.toString()) : null);
			}
		}
		return list;
	}

	/**
	 * 根据区间左右线到history获取实际进度
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@Override
	public List<Object> getActualProgress(Long intervalId, String leftOrRight, Date beginDate, Date endDate) {
		List<Object> res = new ArrayList<Object>();
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			String key = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "A0001");
			IhistorianResponse ir = IhistorianUtil.getDataByKeyAndTime(key, beginDate, endDate);
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				res = (List<Object>) ir.getResult().get("list");
			}
		}
		// 将负数的实际进度替换为0
		for (int i = 0; i < res.size(); i++) {
			int ringNum = (int) res.get(i);
			if (ringNum < 0) {
				res.set(i, 0);
			}
		}
		return res;
	}

	/**
	 * 获取线路某时间段内的进度信息
	 * 
	 * @param lineId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> getLineProcess(Long lineId, Date beginDate, Date endDate) {
		List<Map<String, Object>> processList = new ArrayList<Map<String, Object>>();
		Calendar c = Calendar.getInstance();
		// 开始时间为空且结束时间不为空则以结束时间往前取两年作为开始时间。
		if (beginDate == null && endDate != null) {
			c.setTime(endDate);
			c.add(Calendar.YEAR, -2);
			beginDate = c.getTime();
		}
		// 开始时间不为空且结束时间为空则取当前时间作为结束时间
		if (beginDate != null && endDate == null) {
			beginDate = c.getTime();
		}
		// 查询线路区间信息
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lineId", lineId);
		List<MonitorInfoCity> intervalList = monitorCityDao.findIntervalInfo(params);
		if (intervalList == null || intervalList.size() < 1) {
			return processList;
		}
		// 取进度信息
		Map<String, Object> processMapper = getLineProcessMapper(lineId, beginDate, endDate);
		for (MonitorInfoCity intervalInfo : intervalList) {
			Map<String, Object> intervalMap = new HashMap<String, Object>();
			intervalMap.put("lineNo", intervalInfo.getLineNo());
			intervalMap.put("lineName", intervalInfo.getLineName());
			intervalMap.put("intervalMark", intervalInfo.getIntervalMark());
			intervalMap.put("intervalName", intervalInfo.getIntervalName());
			processList.add(intervalMap);
			String baseKey = "" + intervalInfo.getLineNo() + "#" + intervalInfo.getIntervalMark();
			// 左线进度信息
			List<Object> leftProcessList = new ArrayList<Object>();
			// 左线总进度（总环数）
			Object leftRingNum = processMapper.get(baseKey + "#l" + "#ringNum");
			leftProcessList.add(leftRingNum == null ? 0 : leftRingNum);
			// 左线实际进度
			Object leftActualRingNum = processMapper.get(baseKey + "#l" + "#actualRingNum");
			leftProcessList.add(leftActualRingNum == null ? 0 : leftActualRingNum);
			intervalMap.put("left", leftProcessList);
			// 右线进度信息
			List<Object> rightProcessList = new ArrayList<Object>();
			// 左线总进度（总环数）
			Object rightRingNum = processMapper.get(baseKey + "#r" + "#ringNum");
			rightProcessList.add(rightRingNum == null ? 0 : rightRingNum);
			// 左线实际进度
			Object rightActualRingNum = processMapper.get(baseKey + "#r" + "#actualRingNum");
			rightProcessList.add(rightActualRingNum == null ? 0 : rightActualRingNum);
			intervalMap.put("right", rightProcessList);
		}
		return processList;
	}

	private Map<String, Object> getLineProcessMapper(Long lineId, Date beginDate, Date endDate) {
		Map<String, Object> processMapper = new HashMap<String, Object>();
		// 根据线路查询整个线路下所有工程信息
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lineId", lineId);
		List<MonitorInfoCity> micList = monitorCityDao.findMonitorInfoDatas(params);
		if (micList == null || micList.size() < 1) {
			return processMapper;
		}
		// 取区间左右线的实际进度
		// 如果开始时间，结束时间为空则用当前环号作为实际进度
		Map<String, Object> currentRingMapper = new HashMap<String, Object>();
		if (beginDate == null && beginDate == null) {
			currentRingMapper = getCurrentRingMapper(micList);
		}
		List<String> keys = new ArrayList<String>();
		for (MonitorInfoCity mic : micList) {
			String baseKey = "" + mic.getLineNo() + "#" + mic.getIntervalMark() + "#" + mic.getLeftOrRight();
			processMapper.put(baseKey + "#ringNum", mic.getRingNum());// 总进度
			// 取实际进度
			int totalActual = 0;
			if (beginDate == null && beginDate == null) {
				// 如果开始时间，结束时间为空则用当前环号作为实际进度
				totalActual = currentRingMapper.get(baseKey) == null ? 0 : (int) currentRingMapper.get(baseKey);// 实际进度
			} else {
				// 取时间段内每天的实际进度累加作为实际进度
				List<Object> res = new ArrayList<Object>();
				String key = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(),
						"A0001");
				keys.add(key);
				IhistorianResponse ir = IhistorianUtil.getDataByKeyAndTime(key, beginDate, endDate);
				if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
					res = (List<Object>) ir.getResult().get("list");
				}
				for (int i = 0; i < res.size(); i++) {
					int ringNum = (int) res.get(i);
					if (ringNum < 0) {
						continue;
					}
					totalActual += ringNum;
				}
			}
			processMapper.put(baseKey + "#actualRingNum", totalActual);// 实际进度
		}
		return processMapper;
	}

	/**
	 * 取工程当前环数据
	 * 
	 * @param micList
	 * @return
	 */
	private Map<String, Object> getCurrentRingMapper(List<MonitorInfoCity> micList) {
		Map<String, Object> mapper = new HashMap<String, Object>();
		List<String> keys = new ArrayList<String>();
		for (MonitorInfoCity mic : micList) {
			keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "A0001"));
		}
		IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys);
		if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
			for (MonitorInfoCity mic : micList) {
				int currentRingNum = 0;
				String key = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(),
						"A0001");
				try {
					currentRingNum = Integer.parseInt(ir.getResult().get(key).toString());
				} catch (Exception e) {
					currentRingNum = 0;
					logger.error(e.getMessage());
				}
				String ringNumKey = "" + mic.getLineNo() + "#" + mic.getIntervalMark() + "#" + mic.getLeftOrRight();
				mapper.put(ringNumKey, currentRingNum);
			}
		}
		return mapper;
	}

	/**
	 * 查询字典信息并按单位分组
	 * 
	 * @return
	 */
	public Map<String, Object> getDictionaryGroup() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<MetroDictionary> dictList = dicDao.findDictionaryList(new ArrayList<String>());
		if (dictList == null || dictList.size() < 1) {
			return resultMap;
		}
		List<Map<String, String>> unitList = new ArrayList<Map<String, String>>();
		Map<String, String> unitMapper = UnitProperties.getUnitMapper();
		for (Entry<String, String> entry : unitMapper.entrySet()) {
			Map<String, String> unitMap = new HashMap<String, String>();
			unitMap.put("id", entry.getValue());
			unitMap.put("text", entry.getKey());
			unitList.add(unitMap);
		}
		resultMap.put("unitList", unitList);
		Map<String, List<Map<String, Object>>> unitDictMapper = new HashMap<String, List<Map<String, Object>>>();
		for (MetroDictionary dict : dictList) {
			String dictUnit = CommonUtils.isNotNull(dict.getDicUnit()) ? dict.getDicUnit() : "其他";
			String unitKey = unitMapper.get(dictUnit);
			unitKey = unitKey == null ? "other" : unitKey;
			List<Map<String, Object>> unitDictList = unitDictMapper.get(unitKey);
			if (unitDictList == null) {
				unitDictList = new ArrayList<Map<String, Object>>();
			}
			Map<String, Object> unitDictMap = new HashMap<String, Object>();
			unitDictMap.put("dicName", dict.getDicName());
			unitDictMap.put("dicMean", dict.getDicMean());
			unitDictMap.put("dicPrecision", dict.getDicPrecision());
			unitDictMap.put("dicUnit", dict.getDicUnit());
			unitDictMap.put("dicType", dict.getDicType());
			unitDictList.add(unitDictMap);
			unitDictMapper.put(unitKey, unitDictList);
		}
		resultMap.put("unitMapper", unitDictMapper);
		return resultMap;
	}

	/**
	 * 查询字典信息并按单位分组
	 * 
	 * @return
	 */
	@Override
	public MetroLoopMark findMonitorInfoCityLoop(String lineName, String intervalName, String leftOrRight)
			throws IOException {
		Map<String, Object> params = new HashMap<>();
		params.put("lineName", lineName);
		params.put("intervalName", intervalName);
		params.put("leftOrRight", leftOrRight);
		List<MonitorInfoCity> mics = monitorCityDao.findMonitorInfoCityLoop(params);
		MetroLoopMark list = new MetroLoopMark();
		List<String> keys = new ArrayList<String>();
		for (MonitorInfoCity mic : mics) {
			for (String p : KPS) {
				keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), p));
			}
			list.setMachineType(mic.getMachineType());
		}
		IhistorianResponse ir = IhistorianUtil.getDataByKeysRing(keys);
		if (ir != null && ir.getCode() == 200) {
			Map<String, Object> map = ir.getResult();

			for (MonitorInfoCity mic : mics) {
				keys = new ArrayList<String>();
				for (String p : KPS) {
					keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), p));
				}

				Object obj = map.get(keys.get(0));
				if (obj != null) {
					String A0001 = new ObjectMapper().writeValueAsString(obj);
					Map<String, Object> resultMap = new ObjectMapper().readValue(A0001, HashMap.class);
					list.setA0001(Float.parseFloat(resultMap.get("ring").toString()));
					int quality = StringUtil.nullToInt(resultMap.get("quality").toString());
					list.setCommuniStatus(quality == 100 ? 1 : 0);
				} else {
					list.setA0001(null);
					list.setCommuniStatus(0);
				}
			}
		}
		return list;
	}

	/**
	 * 获取区间左右线通信状态
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	public int getCommuniStatus(Long intervalId, String leftOrRight) {
		int communiStatus = 0;
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		List<String> keys = new ArrayList<String>();
		keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "A0001"));
		IhistorianResponse ir = IhistorianUtil.getDataByKeysRing(keys);
		if (ir != null && ir.getCode() == 200) {
			Map<String, Object> map = ir.getResult();
			Object obj = map.get(keys.get(0));
			if (obj != null) {
				String A0001;
				try {
					A0001 = new ObjectMapper().writeValueAsString(obj);
					Map<String, Object> resultMap = new ObjectMapper().readValue(A0001, HashMap.class);
					int quality = StringUtil
							.nullToInt(resultMap.get("quality") == null ? "" : resultMap.get("quality").toString());
					communiStatus = quality == 100 ? 1 : 0;
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}
			return communiStatus;
		}
		return communiStatus;
	}

	/**
	 * 获取城市线路区间名称
	 * 
	 * @param intervalId
	 *            区间
	 * @param leftOrRight
	 *            左右线
	 * @return
	 */
	@Override
	public MonitorInfoCity findIntervalMonitorInfoDatas(Long intervalId, String leftOrRight) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		return monitorCityDao.findIntervalMonitorInfoDatas(params);
	}

	/**
	 * 区间左右线信息APP（分组基础参数）
	 * 
	 * @param intervalId
	 *            区间
	 * @param leftOrRight
	 *            左右线
	 */
	@Override
	public Map<String, Object> getBaseData(Long intervalId, String leftOrRight) {
		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);

		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			Integer lineNumber = mic.getLineNo();
			Integer intervalMark = mic.getIntervalMark();
			String head = IhistorianUtil.getKey(lineNumber, intervalMark, leftOrRight) + ".";
			List<String> keys = new ArrayList<String>();
			for (String p : KPSS2) {
				keys.add(IhistorianUtil.getKey(lineNumber, intervalMark, leftOrRight, p));
			}
			IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys);
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = ir.getResult();
				for (String key : map2.keySet()) {
					Object value = map2.get(key);
					key = key.replace(head, "");
					key = key.replace(".F_CV", "");
					map.put(key, value);
				}
			}
			map.put("lineName", mic.getLineName());
			map.put("intervalMark", mic.getIntervalMark());
			map.put("intervalName", mic.getIntervalName());
			map.put("leftOrRight", mic.getLeftOrRight());
			map.put("ringNum", mic.getRingNum());
		}

		return map;
	}
	
	/**
	 * 根据区间左右线、开始环号、结束环号转换成这些环号对应的最早开始时间和最晚结束时间
	 * @param intervalId  区间
	 * @param leftOrRight 左右线
	 * @param startRingNo 开始环号
	 * @param endRingNo   结束环号
	 * @return
	 */
	private Map<String, Date> changeRingNoToTime(String intervalId, String leftOrRight, int startRingNo, int endRingNo){
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("startRingNo", startRingNo);
		params.put("endRingNo", endRingNo);
		List<RingStatus> ringStatusList = ringStatusDao.findRingTimeInfo(params);
		Map<String, Date> timeMap = new HashMap<String, Date>();
		if(ringStatusList == null || ringStatusList.size() < 1){
			return timeMap;
		}
		Date beginTime = null;
		Date endTime = null;
		for (RingStatus ringStatus: ringStatusList) {
			if(beginTime == null || beginTime.after(ringStatus.getStartDt())){
				beginTime = ringStatus.getStartDt();
			}
			if(endTime == null || endTime.before(ringStatus.getEndDt())){
				endTime = ringStatus.getEndDt();
			}
		}
		timeMap.put("beginTime", beginTime);
		timeMap.put("endTime", endTime);
		return timeMap;
	}

	/**
	 * 根据区间左右线、开始时间、结束时间获取某段时间推进的开始环、结束环与推进环数
	 * @param intervalId  区间
	 * @param leftOrRight 左右线
	 * @param startRingNo 开始时间
	 * @param endRingNo   结束时间
	 * @return
	 */
	@Override
	public Map<String, Object> findRingNumInfo(Long intervalId, String leftOrRight, Date beginTime, Date endTime){
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		List<RingStatus> ringNumList = ringStatusDao.findRingNumInfo(params);
		Map<String, Object> ringNumMap = new HashMap<String, Object>();
		Integer beginRing = null;
		Integer endRing = null;
		if(ringNumList == null || ringNumList.size() < 1){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar beginCal = Calendar.getInstance();
			beginCal.setTime(DateUtil.formatDate(format.format(new Date()), "yyyy-MM-dd"));
			beginCal.add(Calendar.DATE, -180);
			params.put("beginTime", beginCal.getTime());
			List<RingStatus> ringNumList2 = ringStatusDao.findRingNumInfo(params);
			if(ringNumList2 == null || ringNumList2.size() < 1){
				return ringNumMap;
			}
			for (RingStatus ringStatus: ringNumList2) {
				if(beginRing == null){
					beginRing = ringStatus.getRingNo();
				}
				if(endRing == null ){
					endRing = ringStatus.getRingNo();
				}
				if ( beginRing < ringStatus.getRingNo()) {// 开始环
					beginRing = ringStatus.getRingNo();
				}
				if (endRing < ringStatus.getRingNo()) {// 结束环
					endRing = ringStatus.getRingNo();
				}
			}
			ringNumMap.put("beginRing", beginRing);
			ringNumMap.put("endRing", endRing);
			ringNumMap.put("ringNum", endRing-beginRing);
			return ringNumMap;
		}else {
			for (RingStatus ringStatus: ringNumList) {
				if(beginRing == null){
					beginRing = ringStatus.getRingNo();
				}
				if(endRing == null ){
					endRing = ringStatus.getRingNo();
				}
				if ( beginRing > ringStatus.getRingNo()) {// 开始环
					beginRing = ringStatus.getRingNo();
				}
				if (endRing < ringStatus.getRingNo()) {// 结束环
					endRing = ringStatus.getRingNo();
				}
			}
		}
		ringNumMap.put("beginRing", beginRing-1);
		ringNumMap.put("endRing", endRing);
		ringNumMap.put("ringNum", endRing-(beginRing-1));
		return ringNumMap;
	}

	public MonitorIntervalLrStaticsView findMonitorStaticMaxOrMin(Long intervalId, String leftOrRight, int model,
		 Integer dataModel, Integer dataType, int beginRing, int endRing,String[] ks) {
		MonitorIntervalLrStaticsView misv = new MonitorIntervalLrStaticsView();
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			List<String> keys = new ArrayList<String>();
			for (String paramName : ks) {
				keys.add(
						IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), paramName));
			}
			String KEY_A0001 = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(),
					"A0001");
			keys.add(KEY_A0001);
			List<String> pnamesl = new ArrayList<String>();
			Collections.addAll(pnamesl, ks);
			misv.setpNames(pnamesl);
			IhistorianResponse ir = null;
			String key_head = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), leftOrRight);
			List<String> rings = new ArrayList<String>();
			for (int i = beginRing; i <= endRing; i++) {
					rings.add(String.valueOf(i));
			}
			ir = IhistorianUtil.getDataByKeysAndBeRing1(keys, rings, model, key_head, dataModel, dataType);
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				List<HashMap<String, HashMap>> list = (List<HashMap<String, HashMap>>) ir.getResult().get("list");
				List<String> trKeys = new ArrayList<String>();
				List<MonitorStiaticParamView> values = new ArrayList<MonitorStiaticParamView>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (Map map : list) {
					Iterator i = map.keySet().iterator();
					while (i.hasNext()) {
						String k = i.next().toString();
						Date d = new Date(Long.parseLong(k) * 1000);
						Map<String, Object> iv = (Map) map.get(k);
						String ringNum = String.valueOf(iv.get(KEY_A0001));
						if (dataModel == 100) {
							trKeys.add(sdf.format(d) + " " + ringNum);
						} else {
							trKeys.add(ringNum);
						}
						break;
					}
				}
				int keysSize = keys.size() - 1;
				for (int j = 0; j < keysSize; j++) {
					MonitorStiaticParamView mspv = new MonitorStiaticParamView();
					mspv.setName(pnamesl.get(j));
					mspv.setParam(ks[j]);
					List<Object> data = new ArrayList<Object>();
					for (Map m : list) {
						Iterator ii = m.keySet().iterator();
						while (ii.hasNext()) {
							Map<String, Object> vv = (Map) m.get(ii.next());
							data.add(vv.get(keys.get(j)));
							break;
						}
					}
					mspv.setData(data);
					values.add(mspv);
				}
				misv.setKeys(trKeys);
				misv.setValues(values);
			}
		}
		return misv;
	}

	public Map<String, Object> getMaxOrMinValue(MetroLineIntervalLr lr, String[] ks, Integer beginRing, Integer endRing, boolean isMax) {
		Map<String, Object> map = new HashMap<String, Object>();
		MonitorIntervalLrStaticsView misv = null;
		misv = findMonitorStaticMaxOrMin(lr.getIntervalId(), lr.getLeftOrRight(), 0, (isMax ? 1 : 2), 3, beginRing,
				endRing, ks);
		if (misv == null) {
			return map;
		}
		List<MonitorStiaticParamView> paramViewList = misv.getValues();
		if (paramViewList == null || paramViewList.size() < 1) {
			return map;
		}
		for (MonitorStiaticParamView pv : paramViewList) {
			if (pv.getData() == null || pv.getData().size() < 1) {
				continue;
			}
			Float value = null;
			for (Object dataValue : pv.getData()) {
				if (dataValue == null) {
					continue;
				}
				if (value == null) {
					value = Float.parseFloat(dataValue.toString());
				}
				if (isMax && value < Float.parseFloat(dataValue.toString())) {// 最大值
					value = Float.parseFloat(dataValue.toString());
				}
				if (!isMax && value > Float.parseFloat(dataValue.toString())) {// 最小值
					value = Float.parseFloat(dataValue.toString());
				}
			}
			map.put(pv.getParam(), value);
		}
		return map;
	}
	
	/**
	 * 取区间左右线指定环号参数的最大值和最小值
	 * @param intervalId  区间
	 * @param leftOrRight 左右线
	 * @param beginRing   开始环
	 * @param endRing     结束环
	 * @return
	 */
	public Map<String, Object> getMaxAndMinValue(Long intervalId, String leftOrRight, Integer beginRing, Integer endRing) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MetroLineIntervalLr lr = lineIntervalLrDao.findIntervalLr(params);
		String[] ks = {"A0013","B0001","B0002","B0004","B0006","R0026","R0028","R0025","R0004"};
		if(lr!=null&&"1".equals(lr.getMachineType())){
			String[] ksx = {"A0004","B0001","B0002","B0004","B0006"};
			ks = ksx;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("min", getMaxOrMinValue(lr, ks, beginRing, endRing, false));
		map.put("max", getMaxOrMinValue(lr, ks, beginRing, endRing, true));
		return map;
	}
	
	/**
	 * 取区间左右线一周指定环号参数的最大值和最小值
	 * @param intervalId  区间
	 * @param leftOrRight 左右线
	 * @param beginRing   开始环
	 * @param endRing     结束环
	 * @return
	 */
	public Map<String, Object> getWeekMaxAndMinValue(Long intervalId, String leftOrRight, Integer beginRing, Integer endRing) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MetroLineIntervalLr lr = lineIntervalLrDao.findIntervalLr(params);
		String[] ks = {"A0004","B0001","B0002","B0004","B0006","D0023"};
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("min", getMaxOrMinValue(lr, ks, beginRing, endRing, false));
		map.put("max", getMaxOrMinValue(lr, ks, beginRing, endRing, true));
		return map;
	}
}
