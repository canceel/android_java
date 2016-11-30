package com.allipper.common.service.comm.service;

import java.util.Set;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * Redis集群工厂类
 * @author YXL
 *
 */
public class JedisClusterFactory implements FactoryBean<JedisCluster>,InitializingBean{
	
	/**
	 * 主机集合
	 */
	private Set<HostAndPort> hostset;
	
	/**
	 * redis集群连接
	 */
	 private JedisCluster jedisCluster; 

	 /**
	  * 连接超时时间
	  */
	 private Integer timeout;  
	
	 /**
	  * 集群最大重定向数
	  */
	 private Integer maxRedirections; 
	   
	 /**
	  * 连接池
	  */
	 private GenericObjectPoolConfig genericObjectPoolConfig;  
	    
	 
	 /**
	  * 
	  */
	public void afterPropertiesSet() throws Exception {
        jedisCluster = new JedisCluster(hostset, timeout, maxRedirections,genericObjectPoolConfig);  
	}
	
	
	/**
	 * 取得连接对象
	 */
	public JedisCluster getObject() throws Exception {
		return jedisCluster;
	}

	public Class<?> getObjectType() {
		return (this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class);  
	}

	public boolean isSingleton() {
		return true;
	}


	public void setHostset(Set<HostAndPort> hostset) {
		this.hostset = hostset;
	}

	
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public void setMaxRedirections(Integer maxRedirections) {
		this.maxRedirections = maxRedirections;
	}
	
	public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {  
        this.genericObjectPoolConfig = genericObjectPoolConfig;  
    } 
	
	

}
