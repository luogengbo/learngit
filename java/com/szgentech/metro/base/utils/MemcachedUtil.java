package com.szgentech.metro.base.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.whalin.MemCached.MemCachedClient;

/**
 * Memcached 操作类
 * 
 * @author MAJL
 *
 */
public class MemcachedUtil {
	private static MemCachedClient cache = null;
	private static Logger logger = Logger.getLogger(MemcachedUtil.class);
	/**
	 * 获取memcached client
	 * 
	 * @return
	 */
	private static MemCachedClient getCache() {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		if (cache == null) {
			cache = (MemCachedClient) wac.getBean("memcachedClient");
		}
		return cache;
	}

	/**
	 * 加入缓存
	 * 
	 * @param key
	 * @param value
	 */
	public static void put(String key, Object value) {
		getCache().set(getMd5Key("", key), value);
	}

	/**
	 * 加入缓存
	 * 
	 * @param key
	 * @param value
	 * @param date
	 */
	public static void put(String key, Object value, Date date) {
		getCache().set(getMd5Key("", key), value, date);
	}

	/**
	 * 加入缓存
	 * 
	 * @param key
	 * @param value
	 * @param hashCode
	 */
	public static void put(String key, Object value, int hashCode) {
		getCache().set(getMd5Key("", key), value, hashCode);
	}

	/**
	 * 添加缓存
	 * 
	 * @param key
	 * @param value
	 */
	/*
	 * public static void add(String key, Object value){
	 * System.out.print(key+":"+value+"\n"); getCache().add(key, value); }
	 */
	/**
	 * get值
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		return getCache().get(getMd5Key("", key));
	}

	/**
	 * get值
	 * 
	 * @param key
	 * @param hashCode
	 * @return
	 */
	public static Object get(String key, int hashCode) {
		return getCache().get(getMd5Key("", key), hashCode);
	}

	/**
	 * 移除key
	 * 
	 * @param key
	 */
	public static void remove(String key) {
		getCache().delete(getMd5Key("", key));
	}

	/**
	 * 检查key是否存在
	 * 
	 * @param key
	 * @return
	 */
	public static boolean containsKey(String key) {
		return getCache().keyExists(getMd5Key("", key));
	}

	/**
	 * 获取Memcached的Md5键值（把自定义前缀+特征字符进行MD5后作为缓存键，因为Md5后的键长度相同且不会重复）
	 * 
	 * @param prefix
	 *            前缀
	 * @param feature
	 *            特征字符
	 * @return key值
	 */
	public static String getMd5Key(String prefix, String feature) {
		String str = prefix + feature;
		try {
			return EncoderByMd5(str);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("获取Memcached的Md5键值:"+e.getMessage());
		}
		return null;
	}

	/**
	 * 进行MD5和base64加密
	 * 
	 * @param str
	 *            待加密的字符串
	 * @return 加密后的字符串
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	private static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		String result = DatatypeConverter.printBase64Binary(md5.digest(str.getBytes("utf-8")));
		return result;
	}
}
