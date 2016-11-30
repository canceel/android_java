package com.allipper.common.service.localdemo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class OrderInfoDao {
	public final static String ORDER_CODE = "code";
	public final static String ORDER_NAME = "name";
	public final static String ORDER_INFO = "info";
	public final static String _MESSAGE = "message";
	public final static String _STATUS = "status";
	public final static String SUCCESS_CODE = "20000";
	public final static String ERROR_CODE = "30000";

	private static Logger logger = Logger.getLogger(OrderInfoDao.class);

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 查询订单数据
	 * 
	 * @param orderCode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getOrderInfo(String orderCode)
			throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if (orderCode != null && !"".equals(orderCode)) {
				map.put(ORDER_CODE, orderCode);
			}
			list = sqlSessionTemplate.selectList("orderInfo.getOrderInfo", map);
			return list;
		} catch (Exception e) {
			logger.error(e);
			throw e;
			// TODO: handle exception
		}
	}

	/**
	 * 修改数据
	 * 
	 * @param orderCode
	 * @param name
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public int updateOrderInfo(String orderCode, String name, String info)
			throws Exception {
		int updateResult = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if (orderCode != null && !"".equals(orderCode)) {
				map.put(ORDER_CODE, orderCode);
				if (name != null && !"".equals(name)) {
					map.put(ORDER_NAME, name);
				}
				if (info != null && !"".equals(info)) {
					map.put(ORDER_INFO, info);
				}
				updateResult = sqlSessionTemplate.update(
						"orderInfo.updateOrderInfo", map);
			}

		} catch (Exception e) {
			logger.error(e);
			throw e;
			// TODO: handle exception
		} finally {
			return updateResult;
		}
	}

	/**
	 * 插入数据
	 * 
	 * @param orderCode
	 * @param name
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public int insertOrderInfo(String orderCode, String name, String info)
			throws Exception {
		int insertResult = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			map.put(ORDER_CODE, orderCode);
			map.put(ORDER_NAME, name);
			map.put(ORDER_INFO, info);
			insertResult = sqlSessionTemplate.insert(
					"orderInfo.insertOrderInfo", map);

		} catch (Exception e) {
			logger.error(e);
			throw e;
			// TODO: handle exception
		} finally {
			return insertResult;
		}
	}

	/**
	 * 删除数据
	 * 
	 * @param orderCode
	 * @return
	 * @throws Exception
	 */
	public int delOrderInfo(String orderCode) throws Exception {
		int delResult = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			map.put(ORDER_CODE, orderCode);
			delResult = sqlSessionTemplate.delete("orderInfo.deleteOrderInfo",
					map);
		} catch (Exception e) {
			logger.error(e);
			throw e;
			// TODO: handle exception
		} finally {
			return delResult;
		}
	}

}
