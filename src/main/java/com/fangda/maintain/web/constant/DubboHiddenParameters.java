package com.fangda.maintain.web.constant;

/**
 * @description 通过dubbo
 *              context传递的一些隐藏参数，这些隐藏参数仅供系统级公共逻辑使用，原则上业务功能不允许直接获取和使用这些隐藏参数。
 * @version 1.0.0
 */
public interface DubboHiddenParameters {

	String TRANSACTION_UUID = "__hiddenParam__transactionUuid";

	String SYS_ID = "__hiddenParam__sysId";
	
	String OPENAPI_PARAMS_BASE64 = "__hiddenParam__openApiParamsBase64";

}
