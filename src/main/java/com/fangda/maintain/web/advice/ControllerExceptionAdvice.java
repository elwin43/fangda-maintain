/*
 * Copyright (c) 2015-2025 by HRT All rights reserved
 */
package com.fangda.maintain.web.advice;

import com.fangda.maintain.web.constant.MaintainRestReturnCode;
import com.fangda.maintain.web.domain.JsonResponseObject;
import com.fangda.maintain.web.exception.RestApiException;
import com.fangda.maintain.web.exception.RestApiValidationRejectedException;
import com.fangda.maintain.web.utils.RandomCodeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @description TODO
 * @version 1.0.0
 */
@ControllerAdvice
public class ControllerExceptionAdvice {

	protected final Logger logger = LogManager.getLogger(getClass());
	protected final boolean isLoggerDebugEnabled = logger.isDebugEnabled();

	/**
	 * 捕获并处理Controller所有可能出现的且未被Controller方法内部消化的异常。
	 * 
	 * @return String
	 */
	@ExceptionHandler(Throwable.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	protected JsonResponseObject handleControllerException(Throwable ex) {
		return handleExceptionInternal(ex);
	}

	/**
	 * @param exception
	 * @return 将异常转化为最终可以响应给REST客户端的JSON结果。
	 */
	protected final JsonResponseObject handleExceptionInternal(Throwable exception) {
		JsonResponseObject res = new JsonResponseObject();

		try {
			throw exception;
		} catch (RestApiValidationRejectedException e) {
			// 输入参数校验异常，使用debug打印日志即可，因为此类异常都是由于调用者的输入有误导致的，无需在生产环境时占用日志存储空间
			if (isLoggerDebugEnabled) {
				if (null != e.getCause()) {
					logger.debug("REST接口输入参数未能通过校验，code: {}, msg: {}", e.getErrCode(), e.getMessage(), e);
				} else {
					logger.debug("REST接口输入参数未能通过校验，code: {}, msg: {}", e.getErrCode(), e.getMessage());
				}
			}
			res.setCode(e.getErrCode());
			res.setMsg(e.getMessage());
			return res;
		} catch (RestApiException e) {
			// service层已知业务异常
			if (null != e.getCause()) {
				logger.error("REST层捕获到异常，code: {}, msg: {}", e.getErrCode(), e.getMessage(), e);
			} else {
				logger.error("REST层捕获到异常，code: {}, msg: {}", e.getErrCode(), e.getMessage());
			}
			res.setCode(e.getErrCode());
			res.setMsg(e.getMessage());
			return res;
		} catch (Exception e) {
			if (null != e.getCause()) {
				logger.error("REST层捕获到BleException异常，code: {}, msg: {}", e.getMessage(),  e.getStackTrace());
			} else {
				logger.error("REST层捕获到BleException异常，code: {}, msg: {}", e.getMessage(), e.getStackTrace());
			}
			// 改用符合《HRT应用集成技术规范》的错误码，而不是IBM框架内置的错误码
			res.setCode(MaintainRestReturnCode.ERR_IBM_FRAMEWORK_ERROR.getCode());
			res.setMsg(e.getMessage() + "[" + e.getMessage() + "]");
		} catch (Throwable e) {
			// 其他未知异常
			String unknownErrRefNo = RandomCodeUtils.generateNumber(8);
			logger.error("REST层捕获到未知异常，ErrRef: {}, 异常信息: {}", unknownErrRefNo, e.getMessage(), e);
			res.setCode(MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode());
			res.setMsg("系统繁忙[REST#" + unknownErrRefNo + "]");
		}
		return res;
	}

}
