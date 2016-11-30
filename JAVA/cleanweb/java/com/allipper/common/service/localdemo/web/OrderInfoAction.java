package com.allipper.common.service.localdemo.web;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.allipper.common.service.comm.web.BaseController;
import com.allipper.common.service.localdemo.dao.OrderInfoDao;
import com.allipper.common.service.localdemo.service.OrderInfoService;
import com.sun.org.apache.xml.internal.utils.URI;

@Controller
@RequestMapping("/service/OrderInfoAction.do")
public class OrderInfoAction extends BaseController {

	static Logger logger = Logger.getLogger(OrderInfoAction.class);
	@Autowired
	private OrderInfoService orderInfoService;

	/**
	 * 获取订单列表信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=orderInfo_back", consumes = "application/json")
	public void getOrderInfo(HttpServletRequest request,
			HttpServletResponse response) {
		List<Map<String, Object>> orderInfo = new ArrayList<Map<String, Object>>();
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		String orderCode = "";
		try {
			if (request.getParameter(OrderInfoDao.ORDER_CODE) != null
					&& !"".equals(request.getParameter(OrderInfoDao.ORDER_CODE))) {
				orderCode = request.getParameter(OrderInfoDao.ORDER_CODE);
			}
			orderInfo = this.orderInfoService.getOrderInfo(orderCode);
			for (int i = 0; i < orderInfo.size(); i++) {
				Map<String, Object> orderMap = orderInfo.get(i);
				JSONObject orderResult = new JSONObject();
				orderResult.put(OrderInfoDao.ORDER_CODE,
						orderMap.get(OrderInfoDao.ORDER_CODE) == null ? ""
								: orderMap.get(OrderInfoDao.ORDER_CODE));
				orderResult.put(OrderInfoDao.ORDER_NAME,
						orderMap.get(OrderInfoDao.ORDER_NAME) == null ? ""
								: orderMap.get(OrderInfoDao.ORDER_NAME));
				orderResult.put(OrderInfoDao.ORDER_INFO,
						orderMap.get(OrderInfoDao.ORDER_INFO) == null ? ""
								: orderMap.get(OrderInfoDao.ORDER_INFO));
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
	 * 修改订单
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=orderInfo_update", consumes = "application/json")
	public void updateOrderInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int updateInfo = 0;
		JSONObject result = new JSONObject();
		String orderCode = "";
		String nameString = "";
		String infoString = "";
		try {
			if (request.getParameter(OrderInfoDao.ORDER_CODE).trim() == null
					|| "".equals(request.getParameter(OrderInfoDao.ORDER_CODE)
							.trim())) {
				result.put(OrderInfoDao._MESSAGE, "订单号不能为空");
				result.put(OrderInfoDao._STATUS, OrderInfoDao.ERROR_CODE);
				return;
			}
			orderCode = request.getParameter(OrderInfoDao.ORDER_CODE).trim();
			nameString = (request.getParameter(OrderInfoDao.ORDER_NAME).trim()
					.trim() == null || "".equals(request.getParameter(
					OrderInfoDao.ORDER_NAME).trim())) ? "" : request
					.getParameter(OrderInfoDao.ORDER_NAME).trim();
			infoString = request.getParameter(OrderInfoDao.ORDER_INFO).trim() == null ? ""
					: request.getParameter(OrderInfoDao.ORDER_INFO).trim();

			nameString = new String(nameString.getBytes("iso8859-1"), "UTF-8");
			infoString = new String(infoString.getBytes("iso8859-1"), "UTF-8");
			// 更新
			updateInfo = this.orderInfoService.updateOrderInfo(orderCode,
					nameString, infoString);
			if (updateInfo > 0) {
				result.put(OrderInfoDao._MESSAGE, "修改数据成功");
				result.put(OrderInfoDao._STATUS, OrderInfoDao.SUCCESS_CODE);
			} else {
				result.put(OrderInfoDao._MESSAGE, "修改数据失败");
				result.put(OrderInfoDao._STATUS, OrderInfoDao.ERROR_CODE);
			}
		} catch (Exception e) {
			logger.error(e);
			result.put(OrderInfoDao._MESSAGE, "修改数据失败");
			result.put(OrderInfoDao._STATUS, OrderInfoDao.ERROR_CODE);
			throw e;
			// TODO: handle exception
		} finally {
			jsonService.writeJson(response, result.toString());
			logger.debug(result.toString());
		}
	}

	/**
	 * 新增数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=orderInfo_insert", consumes = "application/json")
	public void insertOrderInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int insertOrderInfo = 0;
		JSONObject result = new JSONObject();
		URI uri = new URI();
		String orderCode = "";
		String nameString = "";
		String infoString = "";
		try {
			if (request.getParameter(OrderInfoDao.ORDER_CODE).trim() == null
					|| "".equals(request.getParameter(OrderInfoDao.ORDER_CODE)
							.trim())) {
				result.put(OrderInfoDao._MESSAGE, "订单号不能为空");
				result.put(OrderInfoDao._STATUS, OrderInfoDao.ERROR_CODE);
				return;
			}

			if (request.getParameter(OrderInfoDao.ORDER_NAME).trim() == null
					|| "".equals(request.getParameter(OrderInfoDao.ORDER_NAME)
							.trim())) {
				result.put(OrderInfoDao._MESSAGE, "name不能为空");
				result.put(OrderInfoDao._STATUS, OrderInfoDao.ERROR_CODE);
				return;
			}

			if (request.getParameter(OrderInfoDao.ORDER_INFO).trim() == null
					|| "".equals(request.getParameter(OrderInfoDao.ORDER_INFO)
							.trim())) {
				result.put(OrderInfoDao._MESSAGE, "info不能为空");
				result.put(OrderInfoDao._STATUS, OrderInfoDao.ERROR_CODE);
				return;
			}
			orderCode = request.getParameter(OrderInfoDao.ORDER_CODE);
			nameString = request.getParameter(OrderInfoDao.ORDER_NAME);
			infoString = request.getParameter(OrderInfoDao.ORDER_INFO);
			nameString = new String(nameString.getBytes("iso8859-1"), "UTF-8");
			infoString = new String(infoString.getBytes("iso8859-1"), "UTF-8");
			// 插入数据
			insertOrderInfo = this.orderInfoService.insertOrderInfo(orderCode,
					nameString, infoString);
			if (insertOrderInfo > 0) {
				result.put(OrderInfoDao._MESSAGE, "插入数据成功");
				result.put(OrderInfoDao._STATUS, OrderInfoDao.SUCCESS_CODE);
			} else {
				result.put(OrderInfoDao._MESSAGE, "新增数据失败");
				result.put(OrderInfoDao._STATUS, OrderInfoDao.ERROR_CODE);
			}
		} catch (Exception e) {
			logger.error(e);
			result.put(OrderInfoDao._MESSAGE, "插入数据失败");
			result.put(OrderInfoDao._STATUS, OrderInfoDao.ERROR_CODE);
			throw e;
			// TODO: handle exception
		} finally {
			jsonService.writeJson(response, result.toString());
			logger.debug(result.toString());
		}
	}

	/**
	 * 删除数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=orderInfo_del", consumes = "application/json")
	public void delOrderInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int delOrderInfo = 0;
		JSONObject result = new JSONObject();
		String orderCode = "";
		try {
			if (request.getParameter(OrderInfoDao.ORDER_CODE).trim() == null
					|| "".equals(request.getParameter(OrderInfoDao.ORDER_CODE)
							.trim())) {
				result.put(OrderInfoDao._MESSAGE, "订单号不能为空");
				result.put(OrderInfoDao._STATUS, OrderInfoDao.ERROR_CODE);
				return;
			}
			orderCode = request.getParameter(OrderInfoDao.ORDER_CODE);
			delOrderInfo = this.orderInfoService.delOrderInfo(orderCode);
			if (delOrderInfo > 0) {
				result.put(OrderInfoDao._MESSAGE, "删除数据成功");
				result.put(OrderInfoDao._STATUS, OrderInfoDao.SUCCESS_CODE);
			} else {
				result.put(OrderInfoDao._MESSAGE, "删除数据失败");
				result.put(OrderInfoDao._STATUS, OrderInfoDao.ERROR_CODE);
			}

		} catch (Exception e) {
			result.put(OrderInfoDao._MESSAGE, "删除数据失败");
			result.put(OrderInfoDao._STATUS, OrderInfoDao.ERROR_CODE);
			logger.error(e);
			throw e;
			// TODO: handle exception
		} finally {
			jsonService.writeJson(response, result.toString());
			logger.debug(result.toString());
		}

	}

	/**
	 * 获取EasyUi所需订单列表信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "method=orderInfo_Ui_back", consumes = "application/json")
	public void getUiOrderInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// EasyUi所需数据格式
		Map<String, Object> hmap = new HashMap<String, Object>();
		List<Map<String, Object>> orderInfo = new ArrayList<Map<String, Object>>();
		String resultData = "";
		String orderCode = "";
		try {
			if (request.getParameter(OrderInfoDao.ORDER_CODE) != null
					&& !"".equals(request.getParameter(OrderInfoDao.ORDER_CODE))) {
				orderCode = request.getParameter(OrderInfoDao.ORDER_CODE);
			}
			orderInfo = this.orderInfoService.getOrderInfo(orderCode);
			hmap.put("rows", orderInfo);
			hmap.put("total", orderInfo.size());
			hmap.put(OrderInfoDao._MESSAGE, "success");
			hmap.put(OrderInfoDao._STATUS, OrderInfoDao.SUCCESS_CODE);
		} catch (Exception e) {
			logger.error(e);
			hmap.put(OrderInfoDao._MESSAGE, "失败");
			hmap.put(OrderInfoDao._STATUS, OrderInfoDao.ERROR_CODE);
			// TODO: handle exception
		} finally {
			resultData = jsonService.getJsonFromMap(hmap);
			System.out.println(resultData);
			jsonService.writeJson(response, "callback(" + resultData + ")");
		}
	}

}
