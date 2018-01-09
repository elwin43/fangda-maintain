package com.fangda.maintain.web.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebCookie {
	private static final Logger LOGGER = LogManager.getLogger(WebCookie.class);
	public static final String USER_TOKEN = "userToken";
	public static final String HRT_DOMAIN =".huaruntong.cn";
	public static final String SET_COOKIE = "Set-Cookie";
	public static String getToken(HttpServletRequest request){
		return getCookieByName(request,USER_TOKEN);
	}
	public static void addCookieByDomain(String domains,HttpServletResponse response,String name,String value){
		if(!StringUtils.isBlank(domains)){
			String[] domainValues = domains.split(",");
			for(String domain : domainValues){
				LOGGER.info("addCookieByDomain cookie domain is :"+domain);
				response.addHeader(SET_COOKIE, name+"="+value+";path=/; domain="+domain);
			}
		}
	}
	public static void addCookie(HttpServletRequest request,HttpServletResponse response,String name,String value){
		List<String> keys = getKeyByName(request, name);
		for(String key : keys){
			String[] keySplits = key.split("_");
			String domain = keySplits[keySplits.length-1];
			if(StringUtils.isBlank(domain)){
				domain = getDomain(request);
			}
			LOGGER.info("cookie domain is :"+domain);
			response.addHeader(SET_COOKIE, key+"="+value+";path=/; domain="+domain);
		}
	}
	public static String getDomain(HttpServletRequest request){
		StringBuffer url = request.getRequestURL();
		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
		tempContextUrl = tempContextUrl.replace("http://", "").replace("/", "");
		return tempContextUrl;
	}
	/**
	 * 根据名字获取cookie
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static String getCookieByName(HttpServletRequest request,String name){
		Map<String,Cookie> cookieMap = ReadCookieMap(request);
		List<String> keys = getKeyByName( request, name);
		String token = null;
		for(String key : keys){
			if(cookieMap.containsKey(key)){
				Cookie cookie = (Cookie)cookieMap.get(key);
				String value = cookie.getValue();
				if(!StringUtils.isBlank(value)){
					token = value;
					break;
				}
			}
		}
		return token;
	}
	/**
	 * 根据名字获取cookie
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static List<Cookie> delCookieByName(HttpServletRequest request,String name){
		Map<String,Cookie> cookieMap = ReadCookieMap(request);
		List<Cookie> cookies = new ArrayList<Cookie>();
		List<String> keys = getKeyByName( request, name);
		for(String key : keys){
			if(cookieMap.containsKey(key)){
				Cookie cookie = (Cookie)cookieMap.get(key);
				cookie.setMaxAge(0);
				cookies.add(cookie);
			}
		}
		return cookies;
	}
	public static List<String> getKeyByName(HttpServletRequest request,String name){
		List<String> keys = new ArrayList<String>();
		keys.add(name+"_"+HRT_DOMAIN);
		keys.add(name+"_"+request.getRemoteAddr());
		keys.add(name+"_"+getDomain(request));
		return keys;
	}


	/**
	 * 将cookie封装到Map里面
	 * @param request
	 * @return
	 */
	public static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
		Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
		Cookie[] cookies = request.getCookies();
		if(null!=cookies){
			for(Cookie cookie : cookies){
				cookieMap.put(getKeyByCookie(cookie), cookie);
			}
		}
		return cookieMap;
	}
	public static String getKeyByCookie(Cookie cookie){
		String key = cookie.getName()+"+"+cookie.getDomain();
		return key;
	}

}