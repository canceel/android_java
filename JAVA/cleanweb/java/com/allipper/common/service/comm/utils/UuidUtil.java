package com.allipper.common.service.comm.utils;

import java.util.UUID;
/**
 * UUID工具类
 * @author chenkusay
 *
 */
public class UuidUtil {
	/**
	 * 生成一个 时间戳+UUID 的字符串
	 * @return String
	 */
	public static String createUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		str = str.replaceAll("-", "").toUpperCase();
		return str;
	}
}
