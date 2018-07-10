package com.szgentech.metro.base.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szgentech.metro.base.security.Tea;
import com.szgentech.metro.model.MonitorInfoCity;
import com.szgentech.metro.vo.IhistorianResponse;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class IhistorianUtil {

	private static Logger logger = Logger.getLogger(IhistorianUtil.class);

	private final static String URL = ConfigProperties.getValueByKey("IHISTORIAN_VISIT_URL");
	private final static String CityAbbreviation = ConfigProperties.getValueByKey("CITY_ABBREVIATION");

	/**
	 * Ihistorian 响应结果
	 * 
	 * @param keys
	 *            key集合
	 * @return
	 */
	public static IhistorianResponse getDataByKeys(List<String> keys) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("request", keys);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL, params);
			res = res == null?"":res;
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			if(resMap == null || resMap.get("code") == null){
				return ires;
			}
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				Map<String, Object> resultMap = (Map) resMap.get("result");
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian响应"+e.getMessage());
			return ires;
		}
		return ires;
	}

	/**
	 * Ihistorian 响应结果
	 * 
	 * @param keys
	 *            key集合
	 * @return
	 */
	public static IhistorianResponse getCalcByKeys(List<String> keys) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			Set<String> new_keys = new HashSet<String>(keys);
			map.put("request", new_keys);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/onlinecalc", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				Map<String, Object> resultMap = (Map) resMap.get("result");
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--onlinecalc"+e.getMessage());
			return null;
		}
		return ires;
	}

	/**
	 * Ihistorian 响应结果
	 * 
	 * @param keys
	 *            key集合
	 * @return
	 */
	public static IhistorianResponse getDataByKeysRing(List<String> keys) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			Set<String> new_keys = new HashSet<String>(keys);
			map.put("request", new_keys);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/ringquality", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				Map<String, Object> resultMap = (Map) resMap.get("result");
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian  getDataByKeysRing--返回 null:"+e.getMessage());
			return null;
		}
		return ires;
	}

	/**
	 * Ihistorian 响应结果
	 * 
	 * @param keys
	 *            key集合
	 * @param mileage
	 *            里程
	 * @return
	 */
	public static IhistorianResponse getDataByKeys(List<String> keys, Float mileage) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("request", keys.get(0));
			map.put("mileage", mileage);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/mileage2ts", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				Map<String, Object> resultMap = (Map) resMap.get("result");
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--mileage2ts--返回 null"+e.getMessage());
			return null;
		}
		return ires;
	}

	/**
	 * Ihistorian 响应结果
	 * 
	 * @param keys
	 *            key集合
	 * @param timestamps
	 *            里程
	 * @return
	 */
	public static IhistorianResponse getDataByKeys(List<String> keys, List<String> timestamps) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("request", keys);
			map.put("timestamp", timestamps);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/timestamp1", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				List<HashMap> list = (List) resMap.get("result");
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--timestamp1--返回 null:"+e.getMessage());
			return null;
		}
		return ires;
	}

	/**
	 * 获取key
	 * 
	 * @param lineNo
	 *            线路编号 21
	 * @param intervalMark
	 *            区间标号 16
	 * @param leftOrRight
	 *            左右线标记 L
	 * @param param
	 *            参数名 A0001 来源于字典表
	 * @return GZ21_16L.A0001.F_CV
	 */
	public static String getKey(int lineNo, int intervalMark, String leftOrRight, String param) {
		return CityAbbreviation + lineNo + "_" + intervalMark + leftOrRight.toUpperCase() + "." + param + ".F_CV";
	}

	/**
	 * 获取key
	 * 
	 * @param lineNo
	 *            线路编号 21
	 * @param intervalMark
	 *            区间标号 16
	 * @param leftOrRight
	 *            左右线标记 L
	 * @return GZ21_16L
	 */
	public static String getKey(int lineNo, int intervalMark, String leftOrRight) {
		return CityAbbreviation + lineNo + "_" + intervalMark + leftOrRight.toUpperCase();
	}

	/**
	 * 
	 * @param map
	 * @param mic
	 * @param mapX
	 * @param mapY
	 * @param mapZ
	 * @param mileage
	 * @return
	 */
	public static Map<String, Object> getMapAndMileage(Map<String, Object> map, MonitorInfoCity mic, String mapX,
			String mapY, String mapZ, String mileage) {

		String prefix = CityAbbreviation + mic.getLineNo() + "_" + mic.getIntervalMark()
				+ mic.getLeftOrRight().toUpperCase() + ".";
		mapX = prefix + mapX + ".F_CV";
		mapY = prefix + mapY + ".F_CV";
		mapZ = prefix + mapZ + ".F_CV";
		mileage = prefix + mileage + ".F_CV";

		if (map.get(mileage) == null) {
			return null;
		}

		Map<String, Object> maps = new HashMap<>();
		maps.put("mapX", map.get(mapX));
		maps.put("mapY", map.get(mapY));
		maps.put("mapZ", map.get(mapZ));
		maps.put("mileage", map.get(mileage));

		return maps;
	}

	/**
	 * 根据环号数据获取对应环的水平和垂直纠偏量
	 * 
	 * @param ringarr
	 * @param ks
	 * @return
	 */
	public static IhistorianResponse getDataByKeysAndRingNums(List ringarr, List<String> ks) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("request", ks);
			Collections.sort(ringarr, new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return o1.compareTo(o2);
				}
			});
			map.put("ringarr", ringarr);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/ringcalc", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				List<HashMap<String, HashMap>> list = (List) resMap.get("result");
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--ringcalc--返回 null:"+e.getMessage());
			return null;
		}
		return ires;
	}

	/**
	 * 根据环号数据获取材料消耗量
	 * 
	 * @param keys
	 * @param rings
	 * @return
	 */
	public static IhistorianResponse getDataByKeysAndRings(List<String> keys, List<String> rings) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("request", keys);
			map.put("ringarr", rings);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/maxofring", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				List<HashMap<String, HashMap>> list = (List) resMap.get("result");
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--maxofring--返回 null:"+e.getMessage());
			return null;
		}
		return ires;
	}

	/**
	 * 根据环号数据获取材料消耗量
	 * 
	 * @param keys
	 * @param rings
	 * @return
	 */
	public static IhistorianResponse getDataByKeysAndRingsTime(List<String> keys, List<String> rings) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("request", keys);
			map.put("ringarr", rings);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/fivetagvaluebytsv2", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				List<HashMap<String, HashMap>> list = (List) resMap.get("result");
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--fivetagvaluebytsv2--返回 null:"+e.getMessage());
			return null;
		}
		return ires;
	}

	/**
	 * 根据环号数组获取统计时间
	 * 
	 * @param key
	 * @param rings
	 * @return
	 */
	public static IhistorianResponse getDataByKeyAndRings(String key, List<String> rings) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("request", key);
			map.put("ringarr", rings);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/timecalcbyring", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				List<HashMap<String, HashMap>> list = (List) resMap.get("result");
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--timecalcbyring--返回 null:"+e.getMessage());
			return null;
		}
		return ires;
	}

	/**
	 * 获取每天推进进度
	 * 
	 * @param key
	 * @param bd
	 *            开始日期
	 * @param ed
	 *            结束日期
	 * @return
	 */
	public static IhistorianResponse getDataByKeyAndTime(String key, Date bd, Date ed) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("starttime", String.valueOf(bd.getTime() / 1000));
			map.put("endtime", String.valueOf(ed.getTime() / 1000));
			map.put("field", key);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/ringsdiffbydate", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				List<Object> list = (List) resMap.get("result");
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--ringsdiffbydate--返回 null:"+e.getMessage());
			return null;
		}
		return ires;
	}

	/**
	 * 综合统计
	 * 
	 * @param parse
	 * @param parse2
	 * @param timerequest
	 * @param keys
	 * @return
	 */
	public static IhistorianResponse getDataByKeyAndBeTime(Date parse, Date parse2, String timerequest, List<String> keys) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("starttime", String.valueOf(parse.getTime() / 1000));
			map.put("endtime", String.valueOf(parse2.getTime() / 1000));
			map.put("timerequest", timerequest);
			map.put("fieldrequest", keys);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/summarydatabyts", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				List<HashMap<String, HashMap>> list = (List) resMap.get("result");
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--summarydatabyts--返回 null:"+e.getMessage());
			return null;
		}
		return ires;
	}

	/**
	 * 根据环号数组获取综合数据
	 * 
	 * @param keys
	 *            参数数组
	 * @param rings
	 *            环号数组
	 * @param fixedcount
	 *            模式1-7种 当为0时代表无模式
	 * @param key_h
	 *            到区间左右线key前缀
	 * @return
	 */
	public static IhistorianResponse getDataByKeysAndBeRing(List<String> keys, List<String> rings, int fixedcount, String key_h) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("ringarr", rings);
			map.put("request", keys);
			map.put("fixedcount", fixedcount);
			map.put("identify", key_h);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/fivetagvaluebyringv2", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				List<HashMap<String, HashMap>> list = (List) resMap.get("result");
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--fivetagvaluebyringv2--返回 null:"+e.getMessage());
			return null;
		}
		return ires;
	}

	/**
	 * 根据起始和结束日期获取综合数据
	 * 
	 * @param keys
	 *            参数数组
	 * @param beginTime
	 *            起始日期
	 * @param endTime
	 *            结束日期
	 * @param fixedcount
	 *            模式1-7种 当为0时代表无模式
	 * @param key_h
	 *            到区间左右线key前缀
	 * @return
	 */
	public static IhistorianResponse getDataByKeysAndBeTime(List<String> keys, Date beginTime, Date endTime,
			int fixedcount, String key_h) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("starttime", String.valueOf(beginTime.getTime() / 1000));
			map.put("endtime", String.valueOf(endTime.getTime() / 1000));
			map.put("fixedcount", fixedcount);
			map.put("identify", key_h);
			map.put("request", keys);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/fivetagvaluebytsv2", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				List<HashMap<String, HashMap>> list = (List) resMap.get("result");
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--根据起始和结束日期获取综合数据--返回 null:"+e.getMessage());
			return null;
		}
		return ires;
	}

	public static IhistorianResponse getDataByKeysAndBeRing1(List<String> keys, List<String> rings, int fixedcount,
			String key_h, int valuemode, int workmode) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap();
			map.put("ringarr", rings);
			map.put("request", keys);
			map.put("fixedcount", fixedcount);
			map.put("valuemode", valuemode);
			map.put("workmode", workmode);
			map.put("identify", key_h);
			List<NameValuePair> params = new ArrayList();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/fivetagvaluebyringv3", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = (Map) mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				List<HashMap<String, HashMap>> list = (List) resMap.get("result");
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--fivetagvaluebyringv3--返回 null:"+e.getMessage());
			return null;
		}
		return ires;
	}

	public static IhistorianResponse getDataByKeysAndBeTime1(List<String> keys, Date beginTime, Date endTime,
			int fixedcount, String key_h, int valuemode, int workmode) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap();
			map.put("starttime", String.valueOf(beginTime.getTime() / 1000L));
			map.put("endtime", String.valueOf(endTime.getTime() / 1000L));
			map.put("fixedcount", fixedcount);
			map.put("valuemode", valuemode);
			map.put("workmode", workmode);
			map.put("identify", key_h);
			map.put("request", keys);
			List<NameValuePair> params = new ArrayList();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/fivetagvaluebytsv3", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = (Map) mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")),
					HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				List<HashMap<String, HashMap>> list = (List) resMap.get("result");
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--fivetagvaluebytsv3--返回 null:"+e.getMessage());
			return null;
		}
		return ires;
	}

	/**
	 * 根据环号获取综合数据
	 * 
	 * @param key_h
	 *            到区间左右线key前缀
	 * @return
	 */
	public static IhistorianResponse getDataByLine(String key_h) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("identify", key_h);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/allmileage", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				Map<String, Object> resultMap = (Map) resMap.get("result");
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--allmileage--返回 null:"+e.getMessage());
			return null;
		}
		return ires;
	}

	/**
	 * 根据环号获取平面坐标
	 * 
	 * @param key_h
	 *            到区间左右线key前缀
	 * @return
	 */
	public static IhistorianResponse getCoordinatesByLine(String key_h) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("identify", key_h);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/planecoordinates", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				Map<String, Object> resultMap = (Map) resMap.get("result");
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--planecoordinates--返回 null:"+e.getMessage());
			return null;
		}
		return ires;
	}

	public static IhistorianResponse getExportdatabydatemin(String key, Date exportdate) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("datetime", String.valueOf(exportdate.getTime() / 1000));
			map.put("request", key);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/exportdatabydatemin", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				Map<String, Object> resultMap = (Map) resMap.get("result");
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--exportdatabydatemin--返回 null:"+e.getMessage());
			return null;
		}
		return ires;
	}

	public static IhistorianResponse getExportdatabyringmin(String key, String ring) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("ring", ring);
			map.put("request", key);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/exportdatabyringmin", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				Map<String, Object> resultMap = (Map) resMap.get("result");
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--exportdatabyringmin--返回null:"+e.getMessage());
			return null;
		}
		return ires;
	}

	/**
	 * 获取路线左右线具体的环数
	 * 
	 * @param key
	 *            区间标识，格式为 CityAbbreviation + lineNo + "_" + intervalMark
	 * @return
	 */
	public static IhistorianResponse getTodayRing(String key) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("identify", key);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/todayRing", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				Map<String, Object> resultMap = (Map) resMap.get("result");
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ihistorian--todayRing--return null:"+e.getMessage());
			return null;
		}
		return ires;
	}

}
