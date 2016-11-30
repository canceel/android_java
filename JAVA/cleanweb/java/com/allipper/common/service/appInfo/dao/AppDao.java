package com.allipper.common.service.appInfo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class AppDao {
	public final static String APP_ID = "appid";
	public final static String APP_NAME = "appName";
	public final static String APP_INFO = "appInfo";
	public final static String APP_UPDATEINFO = "updateInfo";
	public final static String APP_IMAGE = "imageUrl";
	public final static String APP_Path = "appPath";
	public final static String APP_VerSion = "appVersion";
	public final static String APP_LASTUPDATE = "lastUpdate";
	
	Logger logger = Logger.getLogger(AppDao.class);
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 查询appinfo数据
	 * 每个appid只有对应的一条数据
	 * 
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getAppListInfo(String appId) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if (appId != null && !"".equals(appId)) {
				map.put(APP_ID, appId);
			}
			list = sqlSessionTemplate.selectList("AppInfo.getAppListInfo", map);
			return list;
		} catch (Exception e) {
			logger.error(e);
			throw e;
			// TODO: handle exception
		}
	}


	/**
	 * 根据appid查询数据
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getAppDetail(String appId) throws Exception{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	HashMap<String, Object> map = new HashMap<String, Object>();
	try {
		if (appId != null && !"".equals(appId)) {
			map.put(APP_ID, appId);
		}
		list = sqlSessionTemplate.selectList("AppInfo.getAppDetail", map);
		return list;
	} catch (Exception e) {
		logger.error(e);
		throw e;
		// TODO: handle exception
	}
		
	}
}
