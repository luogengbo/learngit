package com.szgentech.metro.base.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import jodd.util.PropertiesUtil;

/**
 * 
 * @className:ConfigProperties.java
 * @classDescription:配置文件内容获取工具（用计时方法动态加载配置信息，每隔30秒钟自动加载一次配置文件，无需再重启应用程序了）
 * @author: DANIEL DENG
 * @createTime: 2014年7月31日
 *
 */
public class ConfigProperties {
	private static Logger logger = Logger.getLogger(ConfigProperties.class);
	// 配置文件，在classes目录下
	private static String CONFIG_SETTING_FILE = "properties/config.properties";

	// 加载配置信息对象
	private static Properties properties;

	// 是否第一次加载
	private static boolean isFirstTimes = true;

	// 上次时间（毫秒），用于时间计时
	private static long lastTime = System.currentTimeMillis();

	/**
	 * 私有化构造器
	 */
	private ConfigProperties() {
	}

	/**
	 * 根据配置文件的KEY获取Value
	 * 
	 * @param key
	 * @return
	 */
	public static String getValueByKey(String key) {

		if (isFirstTimes) {
			isFirstTimes = false;
			load();
		} else if (needReload()) {
			load();
		}

		if (properties == null)
			return null;

		return properties.getProperty(key);
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
		try {
			properties = PropertiesUtil
					.createFromFile(ConfigProperties.class.getResource("/").getPath() + CONFIG_SETTING_FILE);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

}
