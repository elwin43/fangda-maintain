/*
 * Copyright (c) 2015-2025 by HRT All rights reserved
 */
package com.fangda.maintain.web.exception;

import com.fangda.maintain.web.constant.MaintainRestReturnCode;

/**
 * @description 此异常表示Rest API应用层校验不通过。
 * @version 1.0.0
 */
public class RestApiValidationRejectedException extends RestApiException {
	private static final long serialVersionUID = -8187419409627201848L;

	public RestApiValidationRejectedException(String msg) {
		super(MaintainRestReturnCode.ERR_REST_INPUT_VALIDATION_REJECTED, msg);
	}

	public RestApiValidationRejectedException(String msg, Throwable cause) {
		super(MaintainRestReturnCode.ERR_REST_INPUT_VALIDATION_REJECTED, msg, cause);
	}

}
