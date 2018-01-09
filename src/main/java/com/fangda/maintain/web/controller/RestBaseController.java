package com.fangda.maintain.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Controller基类
 * 
 */

public class RestBaseController {

	public static final String VIEW = "view";
	public static final String LIST = "list";
	public static final String STATUS = "status";
	public static final String WARN = "warn";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";
	public static final String MESSAGES = "messages";
	public static final String CONTENT = "content";

	static Logger loger = LoggerFactory.getLogger(RestBaseController.class);

	private static Gson GSON = new GsonBuilder()
			.enableComplexMapKeySerialization()
			.setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	/**
	 * AJAX输出，返回null
	 * 
	 * @param content
	 * @param type
	 * @return
	 */
	public String ajax(HttpServletResponse response, String content, String type) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			loger.error("IOException:", e);
		}
		return null;
	}

	protected String getCurrentToken(HttpServletRequest request){
		if (request.getSession().getAttribute("token") != null) {
             return (String)request.getSession().getAttribute("token");
		}else{
			return null;
		}
	}

	protected String getCurrentMobile(HttpServletRequest request){
		if (request.getSession().getAttribute("mobile") != null) {
			return (String)request.getSession().getAttribute("mobile");
		}else{
			return null;
		}
	}

	protected Long getCurrentMemberId(HttpServletRequest request){
		if (request.getSession().getAttribute("memberId") != null) {
			return (Long)request.getSession().getAttribute("memberId");
		}else{
			return null;
		}
	}

	protected Long getCurrentUserId(HttpServletRequest request){
		if (request.getSession().getAttribute("userId") != null) {
			return (Long)request.getSession().getAttribute("userId");
		}else{
			return null;
		}
	}

	protected Long getCurrentUserName(HttpServletRequest request){
		if (request.getSession().getAttribute("userName") != null) {
			return (Long)request.getSession().getAttribute("userName");
		}else{
			return null;
		}
	}

	/**
	 * AJAX输出文本，返回null
	 * 
	 * @param text
	 * @return
	 */
	public String ajaxText(HttpServletResponse response, String text) {
		return ajax(response, text, "text/plain");
	}

	/**
	 * AJAX输出HTML，返回null
	 * 
	 * @param html
	 * @return
	 */
	public String ajaxHtml(HttpServletResponse response, String html) {
		return ajax(response, html, "text/html");
	}

	/**
	 * AJAX输出XML，返回null
	 * 
	 * @param xml
	 * @return
	 */
	public String ajaxXml(HttpServletResponse response, String xml) {
		return ajax(response, xml, "text/xml");
	}

	/**
	 * 根据字符串输出JSON，返回null
	 * 
	 * @param jsonString
	 * @return
	 */
	public String ajaxJson(HttpServletResponse response, String jsonString) {
		return ajax(response, jsonString, "text/html");
	}

	/**
	 * 根据Map输出JSON，返回null
	 * 
	 * @param jsonMap
	 * @return
	 */
	public String ajaxJson(HttpServletResponse response,
			Map<String, String> jsonMap) {
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON警告消息，返回null
	 * 
	 * @param message
	 * @return
	 */
	public String ajaxJsonWarnMessage(HttpServletResponse response,
			String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, WARN);
		jsonMap.put(MESSAGE, message);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON警告消息，返回null
	 * 
	 * @param messages
	 * @return
	 */
	public String ajaxJsonWarnMessages(HttpServletResponse response,
			List<String> messages) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS, WARN);
		jsonMap.put(MESSAGES, messages);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON成功消息，返回null
	 * 
	 * @param message
	 * @return
	 */
	public String ajaxJsonSuccessMessage(HttpServletResponse response,
			String message) {

		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, message);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON成功消息，返回null
	 * 
	 * @param messages
	 * @return
	 */
	public String ajaxJsonSuccessMessages(HttpServletResponse response,
			List<String> messages) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGES, messages);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON错误消息，返回null
	 * 
	 * @param message
	 * @return
	 */
	public String ajaxJsonErrorMessage(HttpServletResponse response,
			String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, ERROR);
		jsonMap.put(MESSAGE, message);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON错误消息，返回null
	 * 
	 * @param messages
	 * @return
	 */
	public String ajaxJsonErrorMessages(HttpServletResponse response,
			List<String> messages) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS, ERROR);
		jsonMap.put(MESSAGES, messages);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 设置页面不缓存
	 */
	public void setResponseNoCache(HttpServletResponse response) {
		response.setHeader("progma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
	}

	/**
	 * 根据Object输出JSON字符串
	 */
	public String getJson(Object jsonObject) {
		return GSON.toJson(jsonObject);
	}

	/**
	 * 根据字符串输出JSON，返回null
	 * 
	 * @param jsonString
	 * @return
	 */
	public String ajaxJsonCache(HttpServletResponse response,
			String jsonString, String cacheTime) {
		return ajaxCache(response, jsonString, "text/html", cacheTime);
	}

	/**
	 * AJAX输出，返回null
	 * 
	 * @param content
	 * @param type
	 * @return
	 */
	public String ajaxCache(HttpServletResponse response, String content,
			String type, String cacheTime) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			setCache(response, cacheTime);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			loger.error(e.getMessage());
		}
		return null;
	}

	public void setCache(HttpServletResponse response, String cacheTime) {
		long now = System.currentTimeMillis();
		long cacheTimeLong = Long.parseLong(cacheTime);
		response.setDateHeader("Expires", now + cacheTimeLong);
		response.setDateHeader("Last-Modified", now - (now % cacheTimeLong));
		response.setHeader("Cache-Control", "max-age=" + cacheTime);
		response.setHeader("Pragma", "Pragma");
	}

	/**
	 * 公共校验参数方法
	 * 
	 * @Methods Name validateParas
	 * @Create In 2014年10月8日 By wangfei
	 * @param parametersBindingResult
	 * @param map
	 * @return boolean
	 */
	protected boolean validateParas(BindingResult parametersBindingResult,
			Map<String, Object> map) {
		if (parametersBindingResult.hasErrors()) {
			List<FieldError> fes = parametersBindingResult.getFieldErrors();
			String checkMsg = fes.get(0).getDefaultMessage();
			map.put("success", false);
			map.put("errorCode", "");
			map.put("msg", checkMsg);
			return false;
		}
		return true;
	}
//
//	/**
//	 * spring API请求格式通用处理
//	 * 
//	 * @Methods Name handleHttpMessageConversionException
//	 * @Create In 2014年10月29日 By wangfei
//	 * @param
//	 * @return String
//	 */
//	@ExceptionHandler({ BleException.class, BindException.class,
//			MethodArgumentNotValidException.class,MerchantServiceException.class,ServiceValidationRejectedException.class })
//	@ResponseStatus(value = HttpStatus.OK)
//	@ResponseBody
//	protected Map<String, Object> handleBusException(Exception ex) {
//		BaseOutputDTO map = new BaseOutputDTO();
//		if (ex instanceof BindException) {
//			BindingResult result = ((BindException) ex).getBindingResult();
//			List<FieldError> fes = result.getFieldErrors();
//			String checkMsg = fes.get(0).getDefaultMessage();
//			loger.info("throw  BindException!cause:{}", ex.getStackTrace());
//			map.setCode(ErrorCode.SYSTEM_GENERAL_ERROR.getCode());
//			map.setMsg(ErrorCode.SYSTEM_GENERAL_ERROR.getMsg());
//		}
//		// 判断异常类型
//		else if (ex instanceof MethodArgumentNotValidException) {
//			// org.springframework.validation.BindException
//			BindingResult result = ((MethodArgumentNotValidException) ex)
//					.getBindingResult();
//			List<FieldError> fes = result.getFieldErrors();
//			loger.info("throw MethodArgumentNotValidException!cause:{}", ex.getStackTrace());
//			String checkMsg = fes.get(0).getDefaultMessage();
//			map.setCode(ErrorCode.SYSTEM_GENERAL_ERROR.getCode());
//			map.setMsg(ErrorCode.SYSTEM_GENERAL_ERROR.getMsg());
//		} else if (ex instanceof BleException){
//			loger.info("throw Exception!cause:{}", ex.getStackTrace());
//			map.setCode(((BleException) ex).getCode());
//			map.setMsg(((BleException) ex).getMessage());
//		}else if (ex instanceof MerchantServiceException){
//			loger.info("throw Exception!cause:{}", ex.getStackTrace());
//			map.setCode(((MerchantServiceException) ex).getCode());
//			map.setMsg(((MerchantServiceException) ex).getMessage());
//		}else if (ex instanceof ServiceValidationRejectedException){
//			loger.info("throw Exception!cause:{}", ex.getStackTrace());
//			map.setCode(((ServiceValidationRejectedException) ex).getCode());
//			map.setMsg(((ServiceValidationRejectedException) ex).getMessage());
//		}
//		
//		//异常返回统一报文
//		Map<String, Object> rootNode = new LinkedHashMap<String, Object>();
//		rootNode.put(MemberConstant.OPENAPI_RESPONSE_NODE_RETURN_CODE, map.getCode());
//		rootNode.put(MemberConstant.OPENAPI_RESPONSE_NODE_RETURN_DESC, map.getMsg());
//
//		rootNode.put(MemberConstant.OPENAPI_RESPONSE_NODE_RETURN_STAMP, DateUtils.currentSecondsDatetime());
//		rootNode.put(MemberConstant.OPENAPI_RESPONSE_NODE_RETURN_DATA, map.getData());
//
//		Map<String, Object> finalResponse = new HashMap<String, Object>();
//		finalResponse.put(MemberConstant.OPENAPI_RESPONSE_NODE_ROOT, rootNode);
//		return finalResponse;
//	}

//	/**
//	 * spring API请求格式通用处理
//	 * 
//	 * @Methods Name handleHttpMessageConversionException
//	 * @Create In 2014年10月29日 By wangfei
//	 * @param
//	 * @return String
//	 */
//	@ExceptionHandler(Exception.class)
//	@ResponseStatus(value = HttpStatus.OK)
//	@ResponseBody
//	protected Map<String, Object> handleCommonException(Exception ex) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		loger.error("throw Exception!cause:{}", ex.getStackTrace());
//		if (ex instanceof HttpMessageConversionException) {
//			map.put("code",
//					ComErrorCodeConstants.ErrorCode.PARA_NORULE_ERROR
//							.getErrorCode());
//			map.put("msg",
//					ComErrorCodeConstants.ErrorCode.PARA_NORULE_ERROR.getMemo());
////			map.put("msg",ex.getMessage());
//		} else if (ex instanceof HttpMediaTypeException) {
//			// 请求类型有误
//			map.put("code", ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode());
//			map.put("msg", "请求类型有误!");
////			map.put("msg",ex.getMessage());
//		} else if (ex instanceof TypeMismatchException) {
//			// 请求类型有误
//			map.put("code", ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode());
//			map.put("msg", "参数类型不匹配!");
////			map.put("msg",ex.getMessage());
//		} else if (ex instanceof MissingServletRequestParameterException) {
//			map.put("code", ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode());
//			map.put("msg", "请检查必传参数!");
////			map.put("msg",ex.getMessage());
//		} else if (ex instanceof BleException) {
//			map.put("code", ((BleException) ex).getCode());
//			map.put("msg", ((BleException) ex).getMessage());
////			map.put("msg",ex.getMessage());
//		} else {
//			map.put("code",
//					ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode());
//			map.put("msg",ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getMemo());
//		}
//		//异常返回统一报文
//		Map<String, Object> rootNode = new LinkedHashMap<String, Object>();
//		rootNode.put(MemberConstant.OPENAPI_RESPONSE_NODE_RETURN_CODE, map.get("code"));
//		rootNode.put(MemberConstant.OPENAPI_RESPONSE_NODE_RETURN_DESC, map.get("msg"));
//
//		rootNode.put(MemberConstant.OPENAPI_RESPONSE_NODE_RETURN_STAMP, DateUtils.currentSecondsDatetime());
//		rootNode.put(MemberConstant.OPENAPI_RESPONSE_NODE_RETURN_DATA, null);
//
//		Map<String, Object> finalResponse = new HashMap<String, Object>();
//		finalResponse.put(MemberConstant.OPENAPI_RESPONSE_NODE_ROOT, rootNode);
//		return finalResponse;
//	}
}
