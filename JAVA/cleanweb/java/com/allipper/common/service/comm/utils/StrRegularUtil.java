package com.allipper.common.service.comm.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.config.Config;


public class StrRegularUtil {
	/**
	 * 验证字符串是否大于1的数字
	 * @param sourceStr
	 * @return
	 */
	public static boolean patternNumber(String sourceStr){
		boolean flag=false;
		Pattern p = Pattern.compile(Config.getString("numberRegular"));
		Matcher m = p.matcher(sourceStr);
		if(m.matches()){
			flag=true;
		}	
		return flag; 
	}
	public static boolean patternEmail(String sourceStr){
		boolean flag=false;
		Pattern p = Pattern.compile(Config.getString("emailRegular"));
		Matcher m = p.matcher(sourceStr);
		if(m.matches()){
			flag=true;
		}	
		return flag; 
	}
	public static boolean patternUrl(String sourceStr){
		boolean flag=false;
		Pattern p = Pattern.compile("^(http|https|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$");
		Matcher m = p.matcher(sourceStr);
		if(m.matches()){
			flag=true;
		}	
		return flag; 
	}
	public static boolean patternNumStr(String sourceStr){
		boolean flag=false;
		Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");
		Matcher m = p.matcher(sourceStr);
		if(m.matches()){
			flag=true;
		}	
		return flag; 
	}
	public static void main(String[] args) {
		System.out.println(patternNumStr("1wNom"));
	}
}
