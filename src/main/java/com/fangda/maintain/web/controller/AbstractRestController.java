/*
 * Copyright (c) 2015-2025 by HRT All rights reserved
 */
package com.fangda.maintain.web.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import com.fangda.maintain.web.constant.MaintainRestReturnCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fangda.maintain.web.domain.BaseOutputDTO;
import com.fangda.maintain.web.domain.JsonResponseObject;

/**
 * @description TODO
 * @version 1.0.0
 */
public abstract class AbstractRestController {

	protected final static SimpleDateFormat TRANSACTION_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	protected final static SimpleDateFormat SEND_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	
	protected final Logger logger = LogManager.getLogger(getClass());
	protected final boolean isLoggerDebugEnabled = logger.isDebugEnabled();

	/**
	 * @Title: createValidErrorMsg
	 * @Description: 组建校验错误信息
	 * @param @param
	 *            result
	 * @param @return
	 * @return BaseOutputDTO
	 * @throws @author
	 *             logic
	 * @date 2016年9月15日 上午11:06:04
	 * 
	 * @deprecated controller层应统一返回JsonResponseObject以便进行OpenAPI统一处理，使用createValidErrorMsgAsRestResponse()代替。
	 */
	protected BaseOutputDTO createValidErrorMsg(BindingResult result) {
		List<FieldError> errors = result.getFieldErrors();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < errors.size(); i++) {
			if (i > 0) {
				sb.append(',');
			}
			sb.append(errors.get(i).getField())
			  .append(errors.get(i).getDefaultMessage());
		}
		BaseOutputDTO outDto = new BaseOutputDTO(MaintainRestReturnCode.ERR_REST_INPUT_VALIDATION_REJECTED.getCode(),
				sb.toString());
		return outDto;
	}

	/**
	 * 将Spring对请求数据绑定过程中累积的Validation错误转化为JsonResponseObject。
	 * 
	 * @param result
	 * @return
	 */
	protected JsonResponseObject createValidErrorMsgAsRestResponse(BindingResult result) {
		List<FieldError> errors = result.getFieldErrors();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < errors.size(); i++) {
			if (i > 0) {
				sb.append(',');
			}
			sb.append(errors.get(i).getField())
			  .append(errors.get(i).getDefaultMessage());
		}
		JsonResponseObject jsonResponse = new JsonResponseObject();
		jsonResponse.setCode(MaintainRestReturnCode.ERR_REST_INPUT_VALIDATION_REJECTED.getCode());
		jsonResponse.setMsg(sb.toString());
		return jsonResponse;
	}

}
