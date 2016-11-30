package com.allipper.common.service.comm.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import java.util.ResourceBundle;

public class Constants {
    //永久缓存
    public static final Cache DICTCACHE = CacheManager.getInstance().getCache("dictCache");
    //临时缓存
    public static final Cache ETERNALCACHE = CacheManager.getInstance().getCache("eternalCache");
}
