package com.szgentech.metro.base.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;


public class DateUtil {
	private static Logger logger = Logger.getLogger(DateUtil.class);
	/**
	 * 将源字符串sformate时间格式转化成目标rformate时间格式
	 * 
	 * @param date
	 * @param sformate
	 * @param rformate
	 * @return
	 */
	public static String getFormatDate(String date, String sformate, String rformate) {
		String dateResultStr = "";
		if (!StringUtils.isEmpty(date)) {
			try {
				Date dateD = new SimpleDateFormat(sformate).parse(date);
				dateResultStr = new SimpleDateFormat(rformate).format(dateD);
			} catch (ParseException e) {
				e.printStackTrace();
				logger.error("将源字符串sformate时间格式转化成目标rformate时间格式"+e.getMessage());
			}
		}
		return dateResultStr;
	}

	/**
	 * 通过时间按指定格式转换成字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null)
			return "";
		String result = null;
		DateFormat formt = new SimpleDateFormat(pattern);
		result = formt.format(date);
		return result;
	}

	/**
	 * 把字符串按指定时间格式转换成date类型
	 * 
	 * @param str
	 * @param srcPattern
	 * @return
	 */
	public static Date formatDate(String str, String srcPattern) {
		try {
			if (StringUtils.isEmpty(str))
				return null;
			SimpleDateFormat df = new SimpleDateFormat(srcPattern);
			Date date = df.parse(str);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("把字符串按指定时间格式转换成date类型"+e.getMessage());
			return null;
		}
	}
	
	   /** 
     * 取指定时间对应周的开始时间并且忽略时分秒
     * 
     */  
     public static Date getWeekStartTime(Date dateTime) {
    	 Date date = null;
    	 if(dateTime == null){
    		 return date;
    	 }
    	 date = ignoreHHmmss(dateTime);
         Calendar cal = Calendar.getInstance();
         cal.setTime(date);
         int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;  
         if (dayOfWeek == 0 ) {  
        	 dayOfWeek = 7 ;  
         }  
         cal.add(Calendar.DATE , -dayOfWeek+1 );  
         return cal.getTime(); 
     }
   
	/**
	 * 取指定时间对应周的结束时间并且忽略时分秒
	 * 
	 */
	public static Date getWeekEndTime(Date dateTime) {
		if (dateTime == null) {
			return dateTime;
		}
		dateTime = getWeekStartTime(dateTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		cal.add(Calendar.DATE, 6);
		return cal.getTime();
	}
    
	/**
	 * 取指定时间对应下周的开始时间并且忽略时分秒
	 * 
	 */
	public static Date getLastWeekStartTime(Date dateTime) {
		if (dateTime == null) {
			return dateTime;
		}
		dateTime = getWeekStartTime(dateTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		cal.add(Calendar.DATE, 7);
		return cal.getTime();
	}
   
	/**
	 * 忽略时分秒
	 * 
	 * @param dateTime
	 * @return
	 */
	public static Date ignoreHHmmss(Date dateTime) {
		Date date = null;
		if (dateTime == null) {
			return date;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = dateFormat.format(dateTime);
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
