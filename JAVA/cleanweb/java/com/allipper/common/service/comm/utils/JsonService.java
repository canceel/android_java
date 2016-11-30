package com.allipper.common.service.comm.utils;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
public class JsonService {

	/**
	 * grid
	 * @param list
	 * @param total
	 * @return
	 */
	
	public void writeJson(HttpServletResponse response, String result) {
        try {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print(result);
            out.flush();
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	public void writeHtml(HttpServletResponse response, String result) {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void writeText(HttpServletResponse response, String result) {
        try {
            response.setContentType("text/plain;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print(result);
            out.flush();
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	//ajax返回json格式
    public  String getJsonFromMap(Map<String,Object> map) {

		JSONObject jsonResult=new JSONObject();
    	Set keySet = map.keySet();
    	for(Object key : keySet){
    		jsonResult.put((String)key, map.get(key));
		}
    	return jsonResult.toString();
    }
    //ajax返回json格式
    public  String getJsonFromMapSS(Map<String,String> map) {
    	
    	JSONObject jsonResult=new JSONObject();
    	Set keySet = map.keySet();
    	for(Object key : keySet){
    		jsonResult.put((String)key, map.get(key));
    	}
    	return jsonResult.toString();
    }
	
    /**
     * 返回json格式,key去除下划线,转驼峰式
     * @param list
     * @return
     */
    public  String getJsonFromList(List list) {

		JSONArray jsonArray=new JSONArray();
		for(Iterator iterator=list.iterator();iterator.hasNext();){
			Map map=(Map)iterator.next();
			Set keySet = map.keySet();
			JSONObject jsonObject=new JSONObject();
			for(Object key : keySet){
				jsonObject.put(StringUtil.toCamel((String)key), map.get(key));
			}
			jsonArray.add(jsonObject);
		}
		return jsonArray.toString();
    }
    /**
     * 返回jsonArray格式,key去除下划线,转驼峰式
     * @param list
     * @return 
     * @return
     */
    public  JSONArray getJsonArrFromList(List list) {
    	
    	JSONArray jsonArray=new JSONArray();
    	for(Iterator iterator=list.iterator();iterator.hasNext();){
    		Map map=(Map)iterator.next();
    		Set keySet = map.keySet();
    		JSONObject jsonObject=new JSONObject();
    		for(Object key : keySet){
    			jsonObject.put(StringUtil.toCamel((String)key), map.get(key));
    		}
    		jsonArray.add(jsonObject);
    	}
    	return jsonArray;
    }
    
    /**
     * 普通返回json格式
     * @param list
     * @return
     */
    public  String getJsonFromList_New(List list) {
    	
    	JSONArray jsonArray=new JSONArray();
    	for(Iterator iterator=list.iterator();iterator.hasNext();){
    		Map map=(Map)iterator.next();
    		Set keySet = map.keySet();
    		JSONObject jsonObject=new JSONObject();
    		for(Object key : keySet){
    			jsonObject.put((String)key, map.get(key));
    		}
    		jsonArray.add(jsonObject);
    	}
    	return jsonArray.toString();
    }
}
