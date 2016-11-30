package com.allipper.common.service.localdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allipper.common.service.localdemo.dao.OrderInfoDao;


@Service
public class OrderInfoService {
	Logger logger = Logger.getLogger(OrderInfoService.class);
	@Autowired
	private OrderInfoDao dao;

	/**
	 * 
	 * 查询订单数据
	 * 
	 * @param orderCode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getOrderInfo(String orderCode)
			throws Exception {
		return this.dao.getOrderInfo(orderCode);
	}

	/**
	 * 更改订单信息
	 * 
	 * @param orderCode
	 * @param name
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public int updateOrderInfo(String orderCode, String name, String info)
			throws Exception {
		return this.dao.updateOrderInfo(orderCode, name, info);
	}

	public int insertOrderInfo(String orderCode, String name, String info)
			throws Exception {
		return this.dao.insertOrderInfo(orderCode, name, info);
	}

	/**
	 * 删除订单数据
	 * @param orderCode
	 * @return
	 * @throws Exception
	 */
	public int delOrderInfo(String orderCode) throws Exception {
		return this.dao.delOrderInfo(orderCode);
	}

}
