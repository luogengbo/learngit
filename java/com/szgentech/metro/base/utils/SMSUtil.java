package com.szgentech.metro.base.utils;

import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.szgentech.metro.base.model.CommonResponse;

public class SMSUtil {

	private static final Logger LOG = Logger.getLogger(SMSUtil.class);

	/**
	 * 发送短信
	 * 
	 * @param to
	 *            发送给谁（手机号码），多个用英文逗号分隔开，如: 13810001000,13810001001
	 * @param templateId
	 *            短信模板ID 如：189065
	 * @param params
	 *            短信内容需要的参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static CommonResponse sendTemlateSMS(String to, String templateId, String[] params) {
		CommonResponse response = new CommonResponse();
		HashMap<String, Object> result = null;
		// 初始化SDK
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
		// *初始化服务器地址和端口 *
		restAPI.init(ConfigProperties.getValueByKey("SMS_HOST"), ConfigProperties.getValueByKey("SMS_PORT"));

		// *初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN *
		restAPI.setAccount(ConfigProperties.getValueByKey("ACCOUNT_SID"), ConfigProperties.getValueByKey("AUTH_TOKEN"));

		// *初始化应用ID *
		restAPI.setAppId(ConfigProperties.getValueByKey("APP_ID"));

		// *调用发送模板短信的接口发送短信 *
		// *参数顺序说明： *
		// *第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号 *
		// *第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。 *
		// *系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
		// *第三个参数是要替换的内容数组。 *
		result = restAPI.sendTemplateSMS(to, templateId, params);
		LOG.info("SMSService.sendTemlateSMS result=" + result);
		if ("000000".equals(result.get("statusCode"))) {
			// 正常返回输出data包体信息（map）
			HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for (String key : keySet) {
				Object object = data.get(key);
				LOG.info(key + " = " + object);
			}
		} else {
			// 异常再重发一次，再次失败则不再重发
			LOG.info("发送异常，重发短信");
			result = restAPI.sendTemplateSMS(to, templateId, params);
			if ("000000".equals(result.get("statusCode"))) {
				// 正常返回输出data包体信息（map）
				HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
				Set<String> keySet = data.keySet();
				for (String key : keySet) {
					Object object = data.get(key);
					LOG.info(key + " = " + object);
				}
				response.setCode(Constants.CODE_SUCCESS);
				return response;
			}
			// 异常返回输出错误码和错误信息
			LOG.error("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
			response.setCode(Constants.CODE_FAIL);
			response.setResult("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
			return response;
		}
		response.setCode(Constants.CODE_SUCCESS);
		return response;
	}
}
