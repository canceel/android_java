package com.allipper.common.service.appInfo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.allipper.common.service.appInfo.dao.AppDao;
import com.allipper.common.service.appInfo.service.AppInfoService;
import com.allipper.common.service.comm.web.BaseController;
import com.allipper.common.service.localdemo.dao.OrderInfoDao;

@Controller
@RequestMapping("/service/AppAction.do")
public class AppInfoAction extends BaseController {

	Logger logger = Logger.getLogger(AppInfoAction.class);
	@Autowired
	private AppInfoService service;

	/**
	 * 获取app列表信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=appList", consumes = "application/json")
	public void getAppList(HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("------------------" + request.getRemoteAddr());
		List<Map<String, Object>> appList = new ArrayList<Map<String, Object>>();
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		String appId = "";
		try {
			if (request.getParameter(AppDao.APP_ID) != null
					&& !"".equals(request.getParameter(AppDao.APP_ID))) {
				appId = request.getParameter(AppDao.APP_ID);
			}
			appList = this.service.getAppListInfo(appId);
			for (int i = 0; i < appList.size(); i++) {
				Map<String, Object> orderMap = appList.get(i);
				JSONObject orderResult = new JSONObject();
				orderResult.put(
						AppDao.APP_ID,
						orderMap.get(AppDao.APP_ID) == null ? "" : orderMap
								.get(AppDao.APP_ID));
				orderResult.put(
						AppDao.APP_NAME,
						orderMap.get(AppDao.APP_NAME) == null ? "" : orderMap
								.get(AppDao.APP_NAME));
				orderResult.put(
						AppDao.APP_INFO,
						orderMap.get(AppDao.APP_INFO) == null ? "" : orderMap
								.get(AppDao.APP_INFO));
				orderResult.put(AppDao.APP_UPDATEINFO,
						orderMap.get(AppDao.APP_UPDATEINFO) == null ? ""
								: orderMap.get(AppDao.APP_UPDATEINFO));
				orderResult.put(AppDao.APP_IMAGE,
						orderMap.get(AppDao.APP_IMAGE) == null ? ""
								: "http://10.1.114.173:8080/cleanweb/img/"
										+ orderMap.get(AppDao.APP_IMAGE));
				orderResult.put(AppDao.APP_Path,
						orderMap.get(AppDao.APP_Path) == null ? ""
								: "http://10.1.114.173:8080/cleanweb/appSpace/"
										+ orderMap.get(AppDao.APP_Path));
				orderResult.put(AppDao.APP_VerSion,
						orderMap.get(AppDao.APP_VerSion) == null ? ""
								: orderMap.get(AppDao.APP_VerSion));
				orderResult.put(AppDao.APP_LASTUPDATE,
						orderMap.get(AppDao.APP_LASTUPDATE) == null ? ""
								: orderMap.get(AppDao.APP_LASTUPDATE));
				jsonArray.add(orderResult);
			}
			result.put("result", jsonArray);
			result.put(OrderInfoDao._MESSAGE, "操作成功");
			result.put(OrderInfoDao._STATUS, OrderInfoDao.SUCCESS_CODE);
		} catch (Exception e) {
			logger.error(e);
			result.put(OrderInfoDao._MESSAGE, "失败");
			result.put(OrderInfoDao._STATUS, OrderInfoDao.ERROR_CODE);
			// TODO: handle exception
		} finally {
			jsonService.writeJson(response, result.toString());
			logger.debug(result.toString());
		}
	}

	/**
	 * 根据appid查询数据
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=appDetail", consumes = "application/json")
	public void getAppDetail(HttpServletRequest request,
			HttpServletResponse response) {
		List<Map<String, Object>> appInfo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> appList = new ArrayList<Map<String, Object>>();
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		String appId = "";
		try {
			if (request.getParameter(AppDao.APP_ID) != null
					&& !"".equals(request.getParameter(AppDao.APP_ID))) {
				appId = request.getParameter(AppDao.APP_ID);
				appInfo = this.service.getAppListInfo(appId);
				/**
				 * app信息
				 */
				if (appInfo.size() != 0) {
					Map<String, Object> appMap = appInfo.get(0);
					result.put(
							AppDao.APP_ID,
							appMap.get(AppDao.APP_ID) == null ? "" : appMap
									.get(AppDao.APP_ID));
					result.put(
							AppDao.APP_NAME,
							appMap.get(AppDao.APP_NAME) == null ? "" : appMap
									.get(AppDao.APP_NAME));
					result.put(
							AppDao.APP_INFO,
							appMap.get(AppDao.APP_INFO) == null ? "" : appMap
									.get(AppDao.APP_INFO));
					result.put(AppDao.APP_UPDATEINFO,
							appMap.get(AppDao.APP_UPDATEINFO) == null ? ""
									: appMap.get(AppDao.APP_UPDATEINFO));
					result.put(AppDao.APP_VerSion,
							appMap.get(AppDao.APP_VerSion) == null ? ""
									: appMap.get(AppDao.APP_VerSion));
					result.put(AppDao.APP_LASTUPDATE,
							appMap.get(AppDao.APP_LASTUPDATE) == null ? ""
									: appMap.get(AppDao.APP_LASTUPDATE));
					result.put(AppDao.APP_IMAGE,
							appMap.get(AppDao.APP_IMAGE) == null ? ""
									: "http://10.1.114.173:8080/cleanweb/img/"
											+ appMap.get(AppDao.APP_IMAGE));
					result.put(
							AppDao.APP_Path,
							appMap.get(AppDao.APP_Path) == null ? ""
									: "http://10.1.114.173:8080/cleanweb/appSpace/"
											+ appMap.get(AppDao.APP_Path));
				}
				/**
				 * 列表数据
				 */
				appList = this.service.getAppDetail(appId);
				for (int i = 0; i < appList.size(); i++) {
					Map<String, Object> orderMap = appList.get(i);
					JSONObject orderResult = new JSONObject();
					orderResult.put(
							AppDao.APP_ID,
							orderMap.get(AppDao.APP_ID) == null ? "" : orderMap
									.get(AppDao.APP_ID));
					orderResult.put(AppDao.APP_NAME,
							orderMap.get(AppDao.APP_NAME) == null ? ""
									: orderMap.get(AppDao.APP_NAME));
					orderResult.put(AppDao.APP_INFO,
							orderMap.get(AppDao.APP_INFO) == null ? ""
									: orderMap.get(AppDao.APP_INFO));
					orderResult.put(AppDao.APP_UPDATEINFO,
							orderMap.get(AppDao.APP_UPDATEINFO) == null ? ""
									: orderMap.get(AppDao.APP_UPDATEINFO));
					orderResult.put(AppDao.APP_IMAGE, orderMap
							.get(AppDao.APP_IMAGE) == null ? ""
							: "http://10.1.114.173:8080/cleanweb/img/"
									+ orderMap.get(AppDao.APP_IMAGE));
					orderResult.put(AppDao.APP_Path, orderMap
							.get(AppDao.APP_Path) == null ? ""
							: "http://10.1.114.173:8080/cleanweb/appSpace/"
									+ orderMap.get(AppDao.APP_Path));
					orderResult.put(AppDao.APP_VerSion,
							orderMap.get(AppDao.APP_VerSion) == null ? ""
									: orderMap.get(AppDao.APP_VerSion));
					orderResult.put(AppDao.APP_LASTUPDATE,
							orderMap.get(AppDao.APP_LASTUPDATE) == null ? ""
									: orderMap.get(AppDao.APP_LASTUPDATE));
					jsonArray.add(orderResult);
				}
				result.put("result", jsonArray);
				result.put(OrderInfoDao._MESSAGE, "操作成功");
				result.put(OrderInfoDao._STATUS, OrderInfoDao.SUCCESS_CODE);
			} else {
				result.put(OrderInfoDao._MESSAGE, "appid不能为空");
				result.put(OrderInfoDao._STATUS, OrderInfoDao.ERROR_CODE);
			}
		} catch (Exception e) {
			logger.error(e);
			result.put(OrderInfoDao._MESSAGE, "失败");
			result.put(OrderInfoDao._STATUS, OrderInfoDao.ERROR_CODE);
			// TODO: handle exception
		} finally {
			jsonService.writeJson(response, result.toString());
			logger.debug(result.toString());
		}
	}

}
