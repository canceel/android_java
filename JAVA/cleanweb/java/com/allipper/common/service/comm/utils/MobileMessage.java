package com.allipper.common.service.comm.utils;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.config.Config;

/**
 * 短信接口PI
 * @author chenkusay
 *
 */
@Component
public class MobileMessage {
	static Logger log = Logger.getLogger(MobileMessage.class);
	public int  getMessager(String phone,String Content) throws Exception{
		int statusCode = 0;
		//调用短信接口方法
		try{
			Map<String,Object> map2 = new HashMap<String,Object>();
			HttpClient httpClient = new HttpClient();
			httpClient.getParams().setAuthenticationPreemptive(true);
			Credentials defaultcreds = new UsernamePasswordCredentials(
					Config.getString("piuser_SMS"), Config.getString("pipwd_SMS"));
			httpClient.getState().setCredentials(AuthScope.ANY, defaultcreds);
			PostMethod postMethod = new PostMethod(
					Config.getString("url_SMS"));
			map2.put("SMS_SERVER", Config.getString("SMS_SERVER"));
			map2.put("USER_ID", Config.getString("USER_ID"));
			map2.put("PHONENUMBER", phone);
			map2.put("CONTENT", Content);
			JsonService jsonService = new JsonService();
			String data = jsonService.getJsonFromMap(map2);
			postMethod.addRequestHeader("Content-Type", "application/json; charset=UTF-8");			
			RequestEntity entity = new StringRequestEntity(data,"post","UTF-8");
			postMethod.setRequestEntity(entity);
			statusCode = httpClient.executeMethod(postMethod);	
			String result = postMethod.getResponseBodyAsString();
			System.out.println("result="+result);
		}catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw e;
		}
		return statusCode;
	}
}
