/*
 * Copyright (c) 2015-2025 by HRT All rights reserved
 */
package com.fangda.maintain.web.advice;

import com.fangda.maintain.web.domain.OpenApiParams;

/**
 * @description 暂存当前请求对应的OpenAPI参数
 * @version 1.0.0
 */
public abstract class OpenApiThreadContext {

	private static final ThreadLocal<OpenApiParams> openApiParamsThreadLocal = new ThreadLocal<OpenApiParams>();
	private static final ThreadLocal<String> openApiParamsBase64ThreadLocal = new ThreadLocal<String>();

	protected static void setOpenApiParams(OpenApiParams openApiParams) {
		if (null != openApiParams) {
			openApiParamsThreadLocal.set(openApiParams);
		} else {
			openApiParamsThreadLocal.remove();
		}
	}

	protected static void setOpenApiParamsBase64(String openApiParamsBase64) {
		if (null != openApiParamsBase64) {
			openApiParamsBase64ThreadLocal.set(openApiParamsBase64);
		} else {
			openApiParamsBase64ThreadLocal.remove();
		}
	}

	protected static void clearContext() {
		openApiParamsThreadLocal.remove();
	}

	public static OpenApiParams getOpenApiParams() {
		return openApiParamsThreadLocal.get();
	}

	public static String getOpenApiParamsBase64() {
		return openApiParamsBase64ThreadLocal.get();
	}

}
