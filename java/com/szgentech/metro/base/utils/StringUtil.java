package com.szgentech.metro.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class StringUtil {
	private static Logger logger = Logger.getLogger(StringUtil.class);
	public static int nullToInt(String val) {
		if (val == null || "".equals(val)) {
			return 0;
		} else {
			return Integer.parseInt(val);
		}
	}

	public static double nullToDouble(String val) {
		if (val == null || "".equals(val)) {
			return 0.0;
		} else {
			return Double.parseDouble(val);
		}
	}

	public static float nullToFloat(String val) {
		if (val == null || "".equals(val)) {
			return 0;
		} else {
			return Float.parseFloat(val);
		}
	}

	public static Date stringToDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			logger.error("stringToDate:"+e.getMessage());
			return null;
		}
	}

	public static String timeToString(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(date);
		Date d = new Date(lt * 1000);
		return sdf.format(d);
	}

	public static String nullToString(String str) {
		return str != null && !"".equals(str) ? str : " ";
	}

	public static String timeCnToEn(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		try {
			Date d = sdf.parse(date);
			sdf = new SimpleDateFormat("MM/dd/yyyy");
			return sdf.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("timeCnToEn:"+e.getMessage());
		} catch (NullPointerException n) {
			n.printStackTrace();
			logger.error("timeCnToEn:"+n.getMessage());
		}
		return "09/26/2016";
	}

	public static String timeCnToEnAddOne(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		try {
			Date d = sdf.parse(date);

			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.DATE, 1);
			sdf = new SimpleDateFormat("MM/dd/yyyy");
			return sdf.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("timeCnToEnAddOne:"+e.getMessage());
		} catch (NullPointerException n) {
			n.printStackTrace();
			logger.error("timeCnToEnAddOne:"+n.getMessage());
		}
		return "09/26/2016";
	}

}
