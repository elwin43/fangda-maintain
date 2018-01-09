/*
 * Copyright (c) 2015-2025 by HRT All rights reserved
 */
package com.fangda.maintain.web.exception;

import com.fangda.maintain.web.constant.IReturnCode;
import com.fangda.maintain.web.constant.MaintainRestReturnCode;
import com.fangda.maintain.web.utils.RandomCodeUtils;

/**
 * @description Rest API接口应用层的顶层异常。
 * @version 1.0.0
 */
public class RestApiException extends RuntimeException {
	private static final long serialVersionUID = 6007962916063766569L;
	private String errCode;
	private String errMsg;

	/**
	 * @param restApiReturnCode
	 */
	public RestApiException(IReturnCode restApiReturnCode) {
		this(restApiReturnCode, null, null);
	}

	/**
	 * @param restApiReturnCode
	 * @param cause
	 */
	public RestApiException(IReturnCode restApiReturnCode, Throwable cause) {
		this(restApiReturnCode, null, cause);
	}

	/**
	 * @param restApiReturnCode
	 * @param overrideErrorMsg
	 */
	public RestApiException(IReturnCode restApiReturnCode, String overrideErrorMsg) {
		this(restApiReturnCode, overrideErrorMsg, null);
	}

	public RestApiException(Throwable cause) {
		this(MaintainRestReturnCode.ERR_UNKNOWN_ERROR, cause);
	}

	/**
	 * @param restApiReturnCode
	 * @param overrideErrorMsg
	 * @param cause
	 */
	public RestApiException(IReturnCode restApiReturnCode, String overrideErrorMsg, Throwable cause) {
		super(buildExceptionMessage(restApiReturnCode, overrideErrorMsg, cause), cause);
		if (null == restApiReturnCode) {
			restApiReturnCode = MaintainRestReturnCode.ERR_UNKNOWN_ERROR;
		}
		this.errCode = restApiReturnCode.getCode();
		this.errMsg = null != overrideErrorMsg ? overrideErrorMsg : restApiReturnCode.getMsg();
	}

	public String getErrCode() {
		return errCode;
	}

	public String getErrMsg() {
		return null != errMsg ? errMsg : getMessage();
	}

	private static String buildExceptionMessage(IReturnCode serviceReturnCode, String overrideErrorMsg,
			Throwable cause) {
		String exMsg;
		if (null != overrideErrorMsg) {
			exMsg = overrideErrorMsg;
		} else if (null != serviceReturnCode) {
			exMsg = serviceReturnCode.getMsg();
		} else {
			exMsg = MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getMsg();
		}

		if (null != cause && !(cause instanceof RestApiException)) {
			String unknownErrRefNo = RandomCodeUtils.generateNumber(8);
			exMsg += "[错误参考号REST#" + unknownErrRefNo + "]";
		}

		return exMsg;
	}

}
