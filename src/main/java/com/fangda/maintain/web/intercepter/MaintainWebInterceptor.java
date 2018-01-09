package com.fangda.maintain.web.intercepter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fangda.maintain.web.constant.MaintainRestReturnCode;
import com.fangda.maintain.web.domain.JsonResponseObject;
import com.fangda.maintain.web.utils.ResponseUtils;
import com.fangda.maintain.web.utils.WebSecurityUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaintainWebInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LogManager.getLogger(MaintainWebInterceptor.class);
	@Autowired
	private String globalParamMatches;
	@Autowired
	private ArrayList<String> globalIgnoreKeys;
	@Autowired
	private ArrayList<String> appointIgnoreUrls;
	@Autowired
	private HashMap<String,List<String>> appointIgnoreUrlKeys;
	
	/*public void setCommonParamRegex(String commonParamRegex) {
		this.commonParamRegex = commonParamRegex;
	}*/

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		Log4j2MDCDatas.setCostBegin(String.valueOf(System.currentTimeMillis()));
		logger.info("Restful invoked begin...");

		// 用于区分mvc:resources, 正常的Controller请求
		if (handler == null || !HandlerMethod.class.isAssignableFrom(handler.getClass())) {
			return true;
		}
		
		// TODO Xss校验
		Map<String, String[]> paramMap = request.getParameterMap();
		String requestUrl = request.getRequestURI();
		if (MapUtils.isNotEmpty(paramMap)) {
			for (String key : paramMap.keySet()) {
				/*try {
					validObject(key,requestUrl);
				} catch (Throwable e) {
					JsonResponseObject jsonResponseObject = new JsonResponseObject();
					jsonResponseObject.setCode(PointsRestReturnCode.ERR_ILLEGAL_HTTP_REQUEST.getCode());
					jsonResponseObject.setMsg(e.getMessage());
					ResponseUtils.responseOutWithJson(response, jsonResponseObject);
					//logger.info("字符中包含了XSS攻击内容");
					return false;
				}*/
				String[] values = paramMap.get(key);
				if (values != null && values.length > 0) {
					for (String value : values) {
						if (WebSecurityUtils.containsXSSStrip(value)) {
							JsonResponseObject jsonResponseObject = new JsonResponseObject();
							jsonResponseObject.setCode(MaintainRestReturnCode.ERR_ILLEGAL_HTTP_REQUEST.getCode());
							jsonResponseObject.setMsg("输入中包含不合法字符");
							ResponseUtils.responseOutWithJson(response, jsonResponseObject);
							logger.info("字符中包含了XSS攻击内容");
							return false;
						}
					}
				}
			}
		}

		// TODO SQL注入
		return true;
	}

	private void validObject(String str,String requestUrl){
		try {
			JSONObject jsonObject = JSONObject.parseObject(str);
			/*if(jsonObject.containsKey(PointsJsonRequestConverter.OPENAPI_JSON_ROOT_NODE_NAME)){
				jsonObject = jsonObject.getJSONObject(PointsJsonRequestConverter.OPENAPI_JSON_ROOT_NODE_NAME);
				if(jsonObject.containsKey(PointsJsonRequestConverter.OPENAPI_JSON_REQDATA_NODE_NAME)){
					jsonObject = jsonObject.getJSONObject(PointsJsonRequestConverter.OPENAPI_JSON_REQDATA_NODE_NAME);
				}
			}*/
			Set<Entry<String, Object>> names = jsonObject.entrySet();
			for(Entry<String, Object> entry : names){
				if("com.alibaba.fastjson.JSONObject".equalsIgnoreCase(entry.getValue().getClass().getName())){
					validObject(entry.toString(),requestUrl);
				}else if("com.alibaba.fastjson.JSONArray".equals(entry.getValue().getClass().getName())){
					validArray(entry.getValue().toString(),requestUrl);
				}else{
					if (WebSecurityUtils.containsXSSStrip(entry.getValue().toString())) {
						throw new RuntimeException(entry.getKey()+":"+entry.getValue()+"含有非法字符");
					}else if(isUnIgnoreProperty(requestUrl,entry.getKey()) && isUnValidStr(entry.getValue())){
						throw new RuntimeException(entry.getKey()+":"+entry.getValue()+"含有非法字符");
					}
				}
			}
		} catch (ClassCastException e) {
			if(e.getMessage().contains("com.alibaba.fastjson.JSONArray")){
				validArray(str,requestUrl);
			}else{
				throw e;
			}
		}
	}
	
	private void validArray(String str,String requestUrl){
		JSONArray jsonArray = JSONArray.parseArray(str);
		for(int i=0;i<jsonArray.size();i++){
			validObject(jsonArray.get(i).toString(),requestUrl);
		}
	}
	
	private boolean isUnValidStr(Object obj){
		if(!"java.lang.String".equalsIgnoreCase(obj.getClass().getName())){
			return false;
		}
		Pattern pattern = Pattern.compile(globalParamMatches);
		Matcher matcher = pattern.matcher(obj.toString());
		if(matcher.matches()){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * @Title: isIgnoreProperty
	 * @Description: 需要校验属性
	 * @param @param requestUrl
	 * @param @param property
	 * @param @return
	 * @return boolean
	 * @throws
	 * @date 2016年10月8日 下午2:38:21
	 */
	private boolean isUnIgnoreProperty(String requestUrl,String property){
		if(appointIgnoreUrls!=null && appointIgnoreUrls.contains(requestUrl)){
			return false;
		}else if(globalIgnoreKeys!=null && globalIgnoreKeys.contains(property)){
			return false;
		}else{
			List<String> keys = appointIgnoreUrlKeys.get(requestUrl);
			if(keys!=null && keys.contains(property)){
				return false;
			}
		}
		return true;
	}
}
