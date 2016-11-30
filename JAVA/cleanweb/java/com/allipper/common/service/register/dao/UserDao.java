package com.allipper.common.service.register.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UserDao {
	public final static String UID_ = "uid_";
	public final static String ACCESS_TOKEN = "name";
	public final static String PWD_ = "pqd_";
	public final static String _MESSAGE = "message";
	public final static String _STATUS = "status";
	public final static String SUCCESS_CODE = "20000";
	public final static String ERROR_CODE = "30000";

}
