package com.fangda.maintain.web.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.fangda.maintain.web.domain.BaseOutputDTO;
import com.fangda.maintain.web.domain.JsonResponseObject;

/**
 * DESCRIPTION : web response utils
 */
public class ResponseUtils {
	private static final Logger logger = LogManager.getLogger(ResponseUtils.class);

	// private static final SimpleDateFormat RETURN_STAMP_FORMAT = new
	// SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	// RETURN_STAMP_FORMAT.format(new Date())
	private static final String STAMP_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
	private static final String OPENAPI_RESPONSE_NODE_ROOT = "RESPONSE";
	// private static final String OPENAPI_RESPONSE_NODE_SIGN = "Sign";
	private static final String OPENAPI_RESPONSE_NODE_RETURN_CODE = "RETURN_CODE";
	private static final String OPENAPI_RESPONSE_NODE_RETURN_STAMP = "RETURN_STAMP";
	private static final String OPENAPI_RESPONSE_NODE_RETURN_DATA = "RETURN_DATA";
	private static final String OPENAPI_RESPONSE_NODE_RETURN_DESC = "RETURN_DESC";

	/**
	 * 使用Response返回Json数据
	 * 
	 * @param response
	 * @param jsonResponseObject
	 */
	public static void responseOutWithJson(HttpServletResponse response, JsonResponseObject jsonResponseObject) {
		// 将实体对象转换为JSON Object转换
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(JSON.toJSONString(jsonResponseObject));
		} catch (IOException e) {
			logger.error("reponse json error ", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 使用Response返回Json数据，object类型
	 * 
	 * @param response
	 * @param obj
	 */
	public static void responseOutWithJson2(HttpServletResponse response, Object obj) {
		// 将实体对象转换为JSON Object转换
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			logger.info("response:====" + JSON.toJSONString(obj) + "====");
			out.append(JSON.toJSONString(obj));
		} catch (IOException e) {
			logger.error("reponse json error ", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 使用Response返回Json数据，openAPI返回格式
	 * 
	 * @param response
	 * @param outputDTO
	 */
	public static void responseOutputOpenAPI(HttpServletResponse response, BaseOutputDTO outputDTO) {

		Map<String, Object> rootNode = new LinkedHashMap<String, Object>();
		// rootNode.put(OPENAPI_RESPONSE_NODE_SIGN, openApiParams.getSign());
		rootNode.put(OPENAPI_RESPONSE_NODE_RETURN_CODE, outputDTO.getCode());
		rootNode.put(OPENAPI_RESPONSE_NODE_RETURN_STAMP, DateUtils.formatDatetime(new Date()));
		rootNode.put(OPENAPI_RESPONSE_NODE_RETURN_DATA, outputDTO);
		rootNode.put(OPENAPI_RESPONSE_NODE_RETURN_DESC, outputDTO.getMsg());
		Map<String, Object> finalResponse = new HashMap<String, Object>();
		finalResponse.put(OPENAPI_RESPONSE_NODE_ROOT, rootNode);

		responseOutWithJson2(response, finalResponse);
	}

	/**
	 * 使用Response返回Json数据
	 * 
	 * @param response
	 * @param outputDTO
	 */
	public static void responseOutput(HttpServletResponse response, BaseOutputDTO outputDTO) {
		// 将实体对象转换为JSON Object转换
		JsonResponseObject json = new JsonResponseObject();
		json.setCode(outputDTO.getCode());
		json.setData(outputDTO);
		json.setMsg(outputDTO.getMsg());
		json.setTransactionUuid(outputDTO.getTransactionUuid());
		responseOutWithJson(response, json);
	}

}
