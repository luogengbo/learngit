package com.szgentech.metro.base.security;

import java.util.Calendar;
import java.util.Random;

/**
 * User: MAJL Date: 2016/3/15 Time: 14:18
 */
public class RandomStringGenerator {
	/**
	 * 获取一定长度的随机字符串
	 * 
	 * @param length
	 *            指定字符串长度
	 * @return 一定长度的字符串
	 */
	public static String getRandomStringByLength(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 获取一天内不重复的10位数字字符窜 算法不够严谨 仍有小概率重复
	 * 
	 * @return
	 */
	public static String getRandomUniqueStringByLength() {
		Calendar ca = Calendar.getInstance();
		long hour = ca.get(Calendar.HOUR_OF_DAY) * 100000000;
		long minu = ca.get(Calendar.MINUTE) * 1000000;
		long sec = ca.get(Calendar.SECOND) * 10000;
		long millsec = ca.get(Calendar.MILLISECOND) * 10;
		long randomNumber = Math.round(Math.random() * (6666999999l - 1) + 1);
		long sum = (hour + minu + sec + millsec + randomNumber);
		String result = String.valueOf(sum);
		if (result.length() == 9) {
			result = "0" + result;
		} else if (result.length() == 8) {
			result = "00" + result;
		} else if (result.length() > 10) {
			result = String.valueOf(6666999999l);
		}
		return result;
	}

	/**
	 * 获取一定长度的随机字符串
	 * 
	 * @param length
	 *            指定字符串长度
	 * @return 一定长度的字符串
	 */
	public static String getScurityCode(int length) {
		String base = "123567890";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

}
