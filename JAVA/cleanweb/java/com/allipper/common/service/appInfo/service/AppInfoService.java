package com.allipper.common.service.appInfo.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allipper.common.service.appInfo.dao.AppDao;

@Service
public class AppInfoService {
	Logger logger = Logger.getLogger(AppInfoService.class);
	@Autowired
	AppDao appDao;

	/**
	 * 
	 * 查询app列表数据
	 * 
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getAppListInfo(String appId)
			throws Exception {
		return this.appDao.getAppListInfo(appId);
	}

	

	/**
	 * 根据appid查询数据
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getAppDetail(String appId)
			throws Exception {
		return this.appDao.getAppDetail(appId);
	}
}
