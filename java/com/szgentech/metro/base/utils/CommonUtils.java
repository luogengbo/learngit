/**
 * 
 */
package com.szgentech.metro.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/**
 * @className:CommonUtils.java
 * @author: luowq
 * @createTime: 2015年10月28日
 */
public class CommonUtils {
	
	private static Logger logger = Logger.getLogger(CommonUtils.class);

	/**
	 * 成功代码
	 */
	public static final int CODE_SUCCESS = 1;
	/**
	 * 失败代码
	 */
	public static final int CODE_FAIL = 0;

	/**
	 * 省份级别
	 */
	public static final int AREA_LEVEL_PROVINCE = 1;
	/**
	 * 城市级别
	 */
	public static final int AREA_LEVEL_CITY = 2;
	/**
	 * 区域级别
	 */
	public static final int AREA_LEVEL_TOWN = 3;

	/**
	 * 从字符串中截取数字
	 * 
	 * @param str
	 * @return
	 */
	public static List<String> getNumberFromString(String str) {
		if (str != null) {
			Pattern p = Pattern.compile("\\d+");
			Matcher m = p.matcher(str.trim());
			List<String> r = new ArrayList<String>();
			while (m.find()) {
				r.add(m.group());
			}
			if (r.size() > 0) {
				return r;
			}
		}
		return null;
	}

	// 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
	/*
	 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * 
	 * 如：
	 * 
	 * X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
	 * 192.168.1.100
	 * 
	 * 用户真实IP为： 192.168.1.110
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		/*
		 * if (ip == null || ip.length() == 0 || "unknown"
		 * .equalsIgnoreCase(ip)) { ip = request.getRemoteAddr(); }
		 */
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("getIpAddr："+e.getMessage());
				}
				ip = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.length() > 15) { // "***.***.***.***".length() = 15
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}

		return ip;
	}

	/**
	 * 判断对象是否不为空
	 * 
	 * @param t
	 * @return
	 */
	public static <T> boolean isNotNull(T t) {
		if (null == t)
			return false;

		if (t instanceof String) {
			String tm = (String) t;
			if ("".equals(tm))
				return false;
		} else if (t instanceof Collection) {
			@SuppressWarnings("rawtypes")
			Collection tm = (Collection) t;
			if (0 == tm.size())
				return false;
		} else if (t instanceof Map) {
			@SuppressWarnings("rawtypes")
			Map tm = (Map) t;
			if (0 == tm.size())
				return false;
		} else if (t.getClass().isArray()) {
			if (t instanceof byte[]) {
				byte[] tm = (byte[]) t;
				if (0 == tm.length)
					return false;
			} else if (t instanceof short[]) {
				short[] tm = (short[]) t;
				if (0 == tm.length)
					return false;
			} else if (t instanceof int[]) {
				int[] tm = (int[]) t;
				if (0 == tm.length)
					return false;
			} else if (t instanceof long[]) {
				long[] tm = (long[]) t;
				if (0 == tm.length)
					return false;
			} else if (t instanceof float[]) {
				float[] tm = (float[]) t;
				if (0 == tm.length)
					return false;
			} else if (t instanceof double[]) {
				double[] tm = (double[]) t;
				if (0 == tm.length)
					return false;
			} else if (t instanceof char[]) {
				char[] tm = (char[]) t;
				if (0 == tm.length)
					return false;
			} else if (t instanceof boolean[]) {
				boolean[] tm = (boolean[]) t;
				if (0 == tm.length)
					return false;
			} else {
				@SuppressWarnings("unchecked")
				T[] tm = (T[]) t;
				if (0 == tm.length)
					return false;
			}
		}
		return true;
	}

	/**
	 * 复制对象属性值(支持不同对象)
	 * 
	 * @param source
	 * @param target
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void copy(Object source, Object target) {
		Class sourceClass = source.getClass();// 得到对象的Class
		Class targetClass = target.getClass();// 得到对象的Class
		Field[] sourceFields = sourceClass.getDeclaredFields();// 得到Class对象的所有属性
		Field[] targetFields = targetClass.getDeclaredFields();// 得到Class对象的所有属性
		for (Field sourceField : sourceFields) {
			String name = sourceField.getName();// 属性名
			Class type = sourceField.getType();// 属性类型
			String methodName = name.substring(0, 1).toUpperCase() + name.substring(1);
			try {
				Method getMethod = sourceClass.getMethod("get" + methodName);// 得到属性对应get方法
				Object value = getMethod.invoke(source);// 执行源对象的get方法得到属性值
				for (Field targetField : targetFields) {
					String targetName = targetField.getName();// 目标对象的属性名
					if (targetName.equals(name)) {
						Method setMethod = targetClass.getMethod("set" + methodName, type);// 属性对应的set方法
						setMethod.invoke(target, value);// 执行目标对象的set方法
					}
				}
			} catch (Exception e) {
				logger.error("复制对象属性值："+e.getMessage());
			}
		}
	}

	/**
	 * 加盐MD5（source.hashCode() + source+source.length() + source.hashCode()）
	 * 
	 * @param source
	 *            目标字符串
	 * @return 加盐MD5字符串
	 */
	public static String getMD5WithSalt(String source) {
		return getMD5(source.hashCode() + source + source.length() + source.hashCode());
	}

	/**
	 * 获取字符串MD5
	 * 
	 * @param source
	 * @return
	 */
	public static String getMD5(String source) {

		String s = null;
		// 用来将字节转换成 16 进制表示的字符
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] b = source.getBytes("UTF-8");
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(b);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {

				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];

				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);

		} catch (Exception e) {
			 e.printStackTrace();
			logger.error("获取字符串MD5："+e.getMessage());
		}
		return s;
	}

	public static String getElementFromXml(String element, String xml) {
		Document doc;
		try {
			doc = DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement();
			String result = rootElt.elementText(element);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getElementFromXml："+e.getMessage());
		}
		return null;
	}

	public static String getImageGenerationFileName(String fileNameSuffix) {
		Date date = new Date();
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
		String generationFileName = simpleFormat.format(date) + new Random().nextInt(1000);
		String filename = generationFileName + "." + fileNameSuffix;
		return filename;
	}
	
	/**
	 * 计算两个日期之间有多少天，不满一天算一天
	 * 
	 * @param startDate   较小的时间
	 * @param endDate     较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date startDate, Date endDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		startDate = sdf.parse(sdf.format(startDate));
		endDate = sdf.parse(sdf.format(endDate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(endDate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算两个日期之间有多少周，不满一周算一周
	 * 
	 * @param startDate 较小的时间
	 * @param endDate   较大的时间
	 * @return 相差周数
	 * @throws ParseException
	 */
	public static int weeksBetween(Date startDate, Date endDate) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(startDate);
		// 取所在周的周一
		calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		int mondayYear = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		int sundayYear = calendar.get(Calendar.YEAR);
		calendar.setTime(startDate);
		int yearStart = calendar.get(Calendar.YEAR);
		int weekStart = mondayYear != sundayYear? (yearStart==mondayYear?53:1):calendar.get(Calendar.WEEK_OF_YEAR);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setFirstDayOfWeek(Calendar.MONDAY);
		calendar1.setTime(endDate);
		calendar1.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		int mondayYear1 = calendar1.get(Calendar.YEAR);
		calendar1.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		int sundayYear1 = calendar1.get(Calendar.YEAR);
		calendar1.setTime(endDate);
		int yearEnd = calendar1.get(Calendar.YEAR);
		int weekEnd = mondayYear1 != sundayYear1? (yearEnd==sundayYear1?1:53):calendar1.get(Calendar.WEEK_OF_YEAR);
		return 53 * (yearEnd - yearStart) + weekEnd - weekStart + 1;
	}

	/**
	 * 计算两个日期之间相差的月数，不满一个月算一个月
	 * 
	 * @param startDate 较小的时间
	 * @param endDate   较大的时间
	 * @return 相差月数
	 * @throws ParseException
	 */
	public static int monthsBetween(Date startDate, Date endDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		startDate = sdf.parse(sdf.format(startDate));
		endDate = sdf.parse(sdf.format(endDate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		cal.setTime(endDate);
		int year1 = cal.get(Calendar.YEAR);
		int month1 = cal.get(Calendar.MONTH);
		return 12 * (year1 - year) + month1 - month + 1;// 两个日期相差几个月，即月份差
	}
	
	/**
	 * 取一个时间段的周期列表信息
	 * @param startDateStr 开始时间
	 * @param endDateStr   结束时间
	 * @param cycleType    周期类型， monthly:月，weekly:周， daily:日
	 * @return
	 */
	public static List<Map<String, Object>> getCycleDateList(String startDateStr, String endDateStr, String cycleType){
		List<Map<String, Object>> cycleList = new ArrayList<Map<String, Object>>();
		if(!CommonUtils.isNotNull(startDateStr) || !CommonUtils.isNotNull(endDateStr)){
			return cycleList;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = sdf.parse(startDateStr);
			endDate =sdf.parse(endDateStr);
			//比较两个日期，调整开始、结束日期，确保startDate小
			Date dt = null;
			if(startDate.compareTo(endDate)>0){
				dt = startDate;
				startDate = endDate;
				endDate = dt;
			}
			if(startDate == null || endDate == null){
				return cycleList;
			}
			cycleType = CommonUtils.isNotNull(cycleType)?cycleType:Constants.CYCLE_TYPE_DAILY;
			if(Constants.CYCLE_TYPE_MONTHLY.equals(cycleType)){
				SimpleDateFormat msdf=new SimpleDateFormat("yyyy-MM");  
				int months = CommonUtils.monthsBetween(startDate, endDate);
				for (int i = 0; i < months; i++) {
					Calendar c = Calendar.getInstance();
					c.setTime(startDate);
					c.add(Calendar.MONTH, i);
					c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
					// 获取当前月最后一天
					Calendar ca = Calendar.getInstance();
					ca.setTime(startDate);
					ca.add(Calendar.MONTH, i);
					ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
					Map<String, Object> tempMap = new HashMap<String, Object>();
					cycleList.add(tempMap);
					tempMap.put("cycleType", cycleType);
					tempMap.put("cycleDate", (i==months-1?endDate:ca.getTime()));
					tempMap.put("beginDate", (i==0?startDate:c.getTime()));
					tempMap.put("endDate", (i==months-1?endDate:ca.getTime()));
					tempMap.put("cycleTitle", msdf.format(i==0?startDate:c.getTime()));
				}
			}else if(Constants.CYCLE_TYPE_WEEKLY.equals(cycleType)){
				int weeks = CommonUtils.weeksBetween(startDate, endDate);
				Date nextDate = startDate;
				for (int i = 0; i < weeks; i++) {
					Calendar c = Calendar.getInstance();
					c.setFirstDayOfWeek(Calendar.MONDAY);  
					c.setTime(nextDate);
					int year = c.get(Calendar.YEAR);
					// 取所在周的周一
					c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
					int mondayYear = c.get(Calendar.YEAR);
					// 取所在周的周日
					Calendar ca = Calendar.getInstance();
					ca.setFirstDayOfWeek(Calendar.MONDAY);  
					ca.setTime(c.getTime());
					ca.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
					int sundayYear = ca.get(Calendar.YEAR);
					// 周跨年且属下一年度，开始时间取本来开始时间即可，不需要取到周一
					if(mondayYear != sundayYear && year == sundayYear){
						c.setTime(nextDate);
					}
					// 周跨年且属上一年度，结束时间取年的最后一天
					if(mondayYear != sundayYear && year == mondayYear){
						ca.clear();
						ca.set(Calendar.YEAR, year);
						ca.roll(Calendar.DAY_OF_YEAR, -1);
					}
					//下一个周期开始时间为当前结束日期+1天
					nextDate = new Date(ca.getTime().getTime()+1000l*60*60*24);
					int weekOfYear = mondayYear != sundayYear? (year==mondayYear?53:1):c.get(Calendar.WEEK_OF_YEAR);
					Map<String, Object> tempMap = new HashMap<String, Object>();
					cycleList.add(tempMap);
					tempMap.put("cycleType", cycleType);
					tempMap.put("cycleDate", (i==weeks-1?endDate:ca.getTime()));
					tempMap.put("beginDate", (i==0?startDate:c.getTime()));
					tempMap.put("endDate", (i==weeks-1?endDate:ca.getTime()));
					String cycleTitle = "第" + weekOfYear + "周" + "(" + sdf.format(i == 0 ? startDate : c.getTime())
							+ "~" + sdf.format(i == weeks-1 ? endDate : ca.getTime()) + ")";
					tempMap.put("cycleTitle", cycleTitle);
				}
			}else{
				// 比较两个日期相差天数，含当天（相差天数加1）
				int days = CommonUtils.daysBetween(startDate, endDate);
				for (int i = 0; i <= days; i++) {
					Map<String, Object> tempMap = new HashMap<String, Object>();
					cycleList.add(tempMap);
					tempMap.put("cycleType", cycleType);
					Date tempDate = new Date(startDate.getTime() + 1000l * 60 * 60 * 24 *i );
					tempMap.put("cycleDate", tempDate);
					tempMap.put("beginDate", tempDate);
					tempMap.put("endDate", tempDate);
					tempMap.put("cycleTitle", sdf.format(tempDate));
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("getCycleDateList parse date error!");
		}
		return cycleList;
	} 

}
