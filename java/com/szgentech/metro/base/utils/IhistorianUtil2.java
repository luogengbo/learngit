package com.szgentech.metro.base.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szgentech.metro.model.Token;
import com.szgentech.metro.vo.IhistorianResponse;

/**
 * 
 * @Title: IhistorianUtil2.java
 * @Package com.szgentech.metro.base.utils
 * @author hjf
 * @date 2018年1月4日 下午3:48:23
 * @version V1.0
 */
public class IhistorianUtil2 {
	private static Logger logger = Logger.getLogger(IhistorianUtil2.class);
	private static String historianServer;
	private static String token;

	private static CloseableHttpClient httpClient = null;

	public IhistorianUtil2() {
	}

	private static String getConfigValue(String key) {
		String value = null;
		if ("HISTORIAN_SERVER".equals(key)) {
			value = "https://office.szgentech.com:8443";
		}
		return value;
	}

	// 获取token
	private static Boolean init() {
		Boolean initSuccess = false;
		historianServer = getConfigValue("HISTORIAN_SERVER");
		try {
			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustAllStrategy()).build();

			httpClient = HttpClients.custom().setSSLContext(sslContext)
					.setSSLHostnameVerifier(new NoopHostnameVerifier()).build();

			HttpPost httpPost = new HttpPost(historianServer + "/uaa/oauth/token");
			httpPost.addHeader("Authorization", "Basic YWRtaW46TWV0cm8xMjM=");
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			nvps.add(new BasicNameValuePair("grant_type", "client_credentials"));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));

			CloseableHttpResponse response = httpClient.execute(httpPost);

			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				HttpEntity httpEntity = response.getEntity();
				String responseString = EntityUtils.toString(httpEntity);
				Token resultToken = new ObjectMapper().readValue(responseString, Token.class);
				token = resultToken.getAccessToken();
				initSuccess = true;

			}
			response.close();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取token:"+e.getMessage());
			
		}
		return initSuccess;
	}

	// 关闭httpclient
	private static void close() {
		try {
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("关闭httpclient:"+e.getMessage());
		}
	}

	/**
	 * Ihistorian 响应结果
	 * 
	 * @param keys
	 *            key集合
	 * @return
	 */
	public static IhistorianResponse getDataByKeys(List<String> keys) {
		IhistorianResponse retString = new IhistorianResponse();
		if (init()) {
			// HttpPost httpPost = new HttpPost(historianServer +
			// "/historian-rest-api/v1/datapoints/sampled");
			HttpPost httpPost = new HttpPost(historianServer + "/historian-rest-api/v1/datapoints/currentvalue");

			httpPost.addHeader("Accept", "application/json");
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.addHeader("Authorization", "Bearer " + token);

			try {
				String keys2 = "";
				for (String string : keys) {
					keys2 += string + ";";
				}
				Map<String, Object> req = new HashMap<String, Object>();
				req.put("tagNames", keys2);

				String params = new ObjectMapper().writeValueAsString(req);
				httpPost.setEntity(new StringEntity(params));

				CloseableHttpResponse response = httpClient.execute(httpPost);

				if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
					HttpEntity httpEntity = response.getEntity();
					String responseString = EntityUtils.toString(httpEntity);
					// 把字符串转成JSON对象
					JSONObject json = new JSONObject(responseString);
					// 得到 JSON 属性对象列表
					JSONArray array3 = null;
					String TagName = null;
					Integer ErrorCode = null;
					//创建一个map用来放查询出来的Value
					Map<String, Object> map = new HashMap<String, Object>();
					JSONArray Array = json.getJSONArray("Data");
					for (int i = 0; i < Array.length(); i++) {
						JSONObject jo = Array.getJSONObject(i);
						for (int j = 3; j < jo.length(); j++) {
							TagName = jo.getString("TagName");
							ErrorCode = jo.getInt("ErrorCode");
							array3 = jo.getJSONArray("Samples");
						}
						for (int k = 0; k < array3.length(); k++) {
							JSONObject jsonObject2 = array3.getJSONObject(k);
							String Value = jsonObject2.getString("Value");
							map.put(TagName, Value);
						}
					}
					if(ErrorCode.equals(0)) {
						retString.setResult(map);
						retString.setCode(200);
						retString.setMessage("message to hint");
						return retString;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("getDataByKeys:"+e.getMessage());
			}finally {
				close();
			}
		}
		return null;
	}

}
