/*
 * Copyright (c) 2015-2025 by HRT All rights reserved
 */
package com.fangda.maintain.web.advice;

import com.fangda.maintain.web.domain.JsonResponseObject;
import com.fangda.maintain.web.utils.TidManager;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.TypeUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description TODO
 * @version 1.0.0
 */
@ControllerAdvice
public class MaintainResponseAdvice implements ResponseBodyAdvice<Object> {

	private static final SimpleDateFormat RETURN_STAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

	private static final String OPENAPI_RESPONSE_NODE_ROOT = "RESPONSE";
	//private static final String OPENAPI_RESPONSE_NODE_SIGN = "Sign";
	private static final String OPENAPI_RESPONSE_NODE_RETURN_CODE = "RETURN_CODE";
	private static final String OPENAPI_RESPONSE_NODE_RETURN_STAMP = "RETURN_STAMP";
	private static final String OPENAPI_RESPONSE_NODE_RETURN_DATA = "RETURN_DATA";
	private static final String OPENAPI_RESPONSE_NODE_RETURN_DESC = "RETURN_DESC";

	private static final String BUSINESS_RESPONSE_TXN_UUID = "transactionUuid";
	private static final String BUSINESS_RESPONSE_DATA = "data";

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return null != returnType.getParameterType()
				&& TypeUtils.isAssignable(JsonResponseObject.class, returnType.getParameterType());
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {

		// 非OpenAPI请求，无需对REST返回报文做加工处理
		if (null == OpenApiThreadContext.getOpenApiParams()) {
			return body;
		}

		//OpenApiParams openApiParams = OpenApiThreadContext.getOpenApiParams();
		JsonResponseObject pointsResponse = (JsonResponseObject) body;

		Map<String, Object> rootNode = new LinkedHashMap<String, Object>();
		//rootNode.put(OPENAPI_RESPONSE_NODE_SIGN, openApiParams.getSign());
		rootNode.put(OPENAPI_RESPONSE_NODE_RETURN_CODE, pointsResponse.getCode());
		rootNode.put(OPENAPI_RESPONSE_NODE_RETURN_STAMP, RETURN_STAMP_FORMAT.format(new Date()));

		Map<String, Object> returnData = new LinkedHashMap<String, Object>();
		if (null != pointsResponse.getTransactionUuid()) {
			returnData.put(BUSINESS_RESPONSE_TXN_UUID, pointsResponse.getTransactionUuid());
		} else {
			String txnUuid = TidManager.getTransactionUuid();
			txnUuid = null == txnUuid ? "" : txnUuid;
			returnData.put(BUSINESS_RESPONSE_TXN_UUID, txnUuid);
		}

		if (null != pointsResponse.getData()) {
			returnData.put(BUSINESS_RESPONSE_DATA, pointsResponse.getData());
		}

		rootNode.put(OPENAPI_RESPONSE_NODE_RETURN_DATA, returnData);
		rootNode.put(OPENAPI_RESPONSE_NODE_RETURN_DESC, pointsResponse.getMsg());

		Map<String, Object> finalResponse = new HashMap<String, Object>();
		finalResponse.put(OPENAPI_RESPONSE_NODE_ROOT, rootNode);
		OpenApiThreadContext.clearContext();
		return finalResponse;
	}

}
