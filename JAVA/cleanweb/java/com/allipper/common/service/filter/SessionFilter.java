package com.allipper.common.service.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allipper.common.service.comm.dao.SpringRedisDao;
import com.allipper.common.service.comm.utils.JsonService;
import com.allipper.common.service.comm.utils.UuidUtil;
import com.config.Config;
@Component
public class SessionFilter implements javax.servlet.Filter {
	Logger log = Logger.getLogger(SessionFilter.class);
	@Autowired
	private SpringRedisDao redisDao;
	
	private FilterConfig config;
	
	
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		String appId=request.getParameter("appId");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        String requestURI = httpRequest.getRequestURI();
        requestURI = requestURI.toLowerCase();
        String requestURL = httpRequest.getRequestURL().toString();
        String path = httpRequest.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        Cookie cookies[]=httpRequest.getCookies();
        String sessionCode = "";
        if(cookies!=null){
        	Cookie readcookie=null;
            for(int i=0;i<cookies.length;i++){
            	 readcookie=cookies[i];
            	 if(readcookie.getName().equals("sessionCode")){
            		 sessionCode=readcookie.getValue();
            		 break;
            	 }
            }
        }
        if(sessionCode==null || sessionCode.equals("")){
        	 sessionCode=UuidUtil.createUUID();
			 Cookie writecookie=new Cookie("sessionCode",sessionCode);
             writecookie.setPath("/");
             httpResponse.addCookie(writecookie);
        }
        //js,css,gif do not run filter
        if(requestURI.endsWith(".js")||requestURI.endsWith(".css")||requestURI.endsWith(".xml")
        		||requestURI.endsWith(".gif")||requestURI.endsWith(".bmp")
        		||requestURI.endsWith(".jpg")||requestURI.endsWith(".png")
        		||requestURI.endsWith(".htm")||requestURI.endsWith(".html")
        		||requestURI.endsWith("newvss")||requestURI.endsWith("newvss/")
        		||requestURI.endsWith("image.jsp")
        		||requestURI.endsWith("joinyhaction.do")
//        		||requestURI.endsWith("logonaction.do")
//				||requestURI.indexOf("vssservice")>0
        		){
        }else{
        	if("GLZX_03".equals(appId)){//pc版登录过滤
        		if(!isLogin("frontend",sessionCode)){//未登陆
        			if(!requestURI.endsWith("logonaction.do") && !requestURI.endsWith("commaction.do")
							&& !requestURI.endsWith("registeraction.do")){
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("statusCode", "301");
						map.put("message", "你的账户已过期，请重新登录！");
						 sessionCode=UuidUtil.createUUID();
						 Cookie writecookie=new Cookie("sessionCode",sessionCode);
			             writecookie.setPath("/");
			             httpResponse.addCookie(writecookie);
						JsonService jsonService = new JsonService();
						String result=jsonService.getJsonFromMap(map);
						jsonService.writeJson(httpResponse, "callback("+result+")");
						return;
					}
        		}
        		
    		}
        } 
        filterChain.doFilter(request,httpResponse);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}
	private boolean isLogin(String type,String sessionCode){
		boolean flag=false;
		if(sessionCode==null || "".equals(sessionCode)){//未登陆
			flag=false;
        }else{
        	try {
    			flag = this.redisDao.exists_str("sessionCode:"+sessionCode);//判断该对象是否在redis中存在
    			this.redisDao.addExpire("sessionCode:"+sessionCode, Integer.parseInt(Config.getString("sessionTimeOut")));
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
        }
		return flag;
	}
}
