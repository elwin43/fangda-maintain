/*
 * Copyright (c) 2015-2025 by HRT All rights reserved
 */
package com.fangda.maintain.web.logging;

import org.apache.logging.log4j.ThreadContext;

/**
 * @description TODO
 * @version 1.0.0
 */
public abstract class Log4j2MDCDatas {
	private static final String MDC_TID = "MDC_TID";
	private static final String MDC_FROM = "MDC_FROM";
	private static final String MDC_API_CODE = "MDC_API_CODE";
	private static final String MDC_API_VERSION = "MDC_API_VERSION";
	private static final String MDC_API_APPNAME = "MDC_API_APPNAME";
	private static final String MDC_ENCODE_METHOD = "MDC_ENCODE_METHOD";
	private static final String MDC_RETURN_FORMAT = "MDC_RETURN_FORMAT";
	private static final String MDC_COST_BEGIN = "MDC_COST_BEGIN";
	private static final String MDC_COST = "MDC_COST";
	private static final String MDC_RETURN_RESULT = "MDC_RETURN_RESULT";
	private static final String MDC_REMARK = "MDC_REMARK";

	public static void setTid(String tid) {
		if (null != tid) {
			ThreadContext.put(MDC_TID, tid);
		} else {
			ThreadContext.remove(MDC_TID);
		}
	}

	public static void setFrom(String from) {
		if (null != from) {
			ThreadContext.put(MDC_FROM, from);
		} else {
			ThreadContext.remove(MDC_FROM);
		}
	}

	public static void setApiCode(String apiCode) {
		if (null != apiCode) {
			ThreadContext.put(MDC_API_CODE, apiCode);
		} else {
			ThreadContext.remove(MDC_API_CODE);
		}
	}

	public static void setApiVersion(String apiVersion) {
		if (null != apiVersion) {
			ThreadContext.put(MDC_API_VERSION, apiVersion);
		} else {
			ThreadContext.remove(MDC_API_VERSION);
		}
	}

	public static void setApiName(String apiName) {
		if (null != apiName) {
			ThreadContext.put(MDC_API_APPNAME, apiName);
		} else {
			ThreadContext.remove(MDC_API_APPNAME);
		}
	}

	public static void setEncodeMethod(String encodeMethod) {
		if (null != encodeMethod) {
			ThreadContext.put(MDC_ENCODE_METHOD, encodeMethod);
		} else {
			ThreadContext.remove(MDC_ENCODE_METHOD);
		}
	}

	public static void setReturnFormat(String returnFormat) {
		if (null != returnFormat) {
			ThreadContext.put(MDC_RETURN_FORMAT, returnFormat);
		} else {
			ThreadContext.remove(MDC_RETURN_FORMAT);
		}
	}

	public static void setCostBegin(String costBegin) {
		if (null != costBegin) {
			ThreadContext.put(MDC_COST_BEGIN, costBegin);
		} else {
			ThreadContext.remove(MDC_COST_BEGIN);
		}
	}

	public static void setCost(String cost) {
		if (null != cost) {
			ThreadContext.put(MDC_COST, cost);
		} else {
			ThreadContext.remove(MDC_COST);
		}
	}

	public static void setReturnResult(String returnResult) {
		if (null != returnResult) {
			ThreadContext.put(MDC_RETURN_RESULT, returnResult);
		} else {
			ThreadContext.remove(MDC_RETURN_RESULT);
		}
	}

	public static void setRemark(String remark) {
		if (null != remark) {
			ThreadContext.put(MDC_REMARK, remark);
		} else {
			ThreadContext.remove(MDC_REMARK);
		}
	}

	public static String getCostBegin() {
		return ThreadContext.get(MDC_COST_BEGIN);
	}

	public static void clearMDCDatas() {
		ThreadContext.remove(MDC_TID);
		ThreadContext.remove(MDC_FROM);
		ThreadContext.remove(MDC_API_CODE);
		ThreadContext.remove(MDC_API_VERSION);
		ThreadContext.remove(MDC_API_APPNAME);
		ThreadContext.remove(MDC_ENCODE_METHOD);
		ThreadContext.remove(MDC_RETURN_FORMAT);
		ThreadContext.remove(MDC_COST_BEGIN);
		ThreadContext.remove(MDC_COST);
		ThreadContext.remove(MDC_RETURN_RESULT);
		ThreadContext.remove(MDC_REMARK);
	}

}
