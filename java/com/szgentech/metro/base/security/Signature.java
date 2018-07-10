package com.szgentech.metro.base.security;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

/**
 * User: MAJL Date: 2016/3/14 Time: 15:23
 */
public class Signature {

	/**
	 * 微信签名算法
	 * 
	 * @param o
	 *            要参与签名的数据对象,排除serialVersionUID
	 * @param wx_pay_pass
	 *            签名key
	 * @return 签名
	 * @throws IllegalAccessException
	 */
	public static String getSign(Object o, String wx_pay_pass) throws IllegalAccessException {
		ArrayList<String> list = new ArrayList<String>();
		Class<?> cls = o.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			if (f.get(o) != null && f.get(o) != "" && !"serialVersionUID".equals(f.getName())) {
				list.add(f.getName() + "=" + f.get(o) + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + wx_pay_pass;
		result = MD5.MD5Encode(result).toUpperCase();
		return result;
	}

	/**
	 * 微信签名算法
	 * 
	 * @param map
	 *            参与签名参数map
	 * @return
	 */
	public static String getSign(Map<String, Object> map, String wx_pay_pass) {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != null && entry.getValue() != "") {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + wx_pay_pass;
		result = MD5.MD5Encode(result).toUpperCase();
		return result;
	}

	/**
	 * 微信签名算法
	 * 
	 * @param json
	 * @return
	 */
	public static String getSign(String json, String wx_pay_pass) {
		JSONObject params = new JSONObject(json);
		Set<?> keys = params.keySet();
		Iterator<?> iterator = keys.iterator();
		ArrayList<String> list = new ArrayList<String>();
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (obj != null && "".equals(obj.toString())) {
				list.add(obj.toString() + "=" + params.get(obj.toString()).toString() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + wx_pay_pass;
		result = MD5.MD5Encode(result).toUpperCase();
		return result;
	}

}
