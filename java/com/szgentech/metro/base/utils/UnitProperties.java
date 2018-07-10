package com.szgentech.metro.base.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

public class UnitProperties {
	private static Logger logger = Logger.getLogger(UnitProperties.class);
	// 配置文件，在classes目录下
	private static String CONFIG_SETTING_FILE = "properties/unit.properties";

	// 加载配置信息对象
	private static Properties properties;

	// 是否第一次加载
	private static boolean isFirstTimes = true;

	// 上次时间（毫秒），用于时间计时
	private static long lastTime = System.currentTimeMillis();

	/**
	 * 私有化构造器
	 */
	private UnitProperties() {
	}

	/**
	 * 根据配置文件的KEY获取Value
	 * 
	 * @param key
	 * @return
	 */
	public static Map<String, String> getUnitMapper() {
		Map<String, String> unitMapper = new HashMap<String, String>();
		if (isFirstTimes) {
			isFirstTimes = false;
			load();
		} else if (needReload()) {
			load();
		}

		if (properties == null) {
			return unitMapper;
		}
		Set<Object> keySet = properties.keySet();
		for (Object key : keySet) {
			unitMapper.put(String.valueOf(properties.getProperty(String.valueOf(key))), String.valueOf(key));
		}
		return unitMapper;
	}

	/**
	 * 是否需要重新加载配置文件（每30秒加载一次）
	 * 
	 * @return true 需要重新加载，false 不需要加载
	 */
	private static boolean needReload() {
		if (System.currentTimeMillis() - lastTime > 30 * 1000) {
			lastTime = System.currentTimeMillis();
			return true;
		}
		return false;
	}

	/**
	 * 加载配置文件
	 */
	private static void load() {
		InputStream in = null;
		try {
			properties = new Properties();
			in = new BufferedInputStream(
					new FileInputStream(UnitProperties.class.getResource("/").getPath() + CONFIG_SETTING_FILE));
			properties.load(new InputStreamReader(in, "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("加载配置文件:"+e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("加载配置文件:"+e.getMessage());
				}
			}
		}
	}

}
