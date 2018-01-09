package com.fangda.maintain.web.domain;

import java.io.Serializable;

/**
 * @description 透过Open API集成网关传递过来的公共参数。
 * @version 1.0.0
 */
public class OpenApiParams implements Serializable {

	private static final long serialVersionUID = 3198517704280452200L;

	// 分配给调用方的唯一身份标识
	String appSubId;

	// 调用方授权令牌
	String appToken;

	// API编码
	String apiId;

	// API版本号
	String apiVersion;

	// 时间戳，格式为yyyy-mm-ddHH:mm:ss，时区为GMT+8
	String timeStamp;

	// 生成服务请求签名字符串所使用的算法类型，目前仅支持MD5
	String signMethod;

	// 服务请求的签名字符串
	String sign;

	// 响应格式（可选）,默认为json格式，可选值：xml或json
	String format;

	// 合作伙伴身份标识
	String partnerId;

	// 合作伙伴系统编码（最长16位）
	String sysId;

	// 被调用API的APP名称（可选）
	String appPubId;

	public final String getAppSubId() {
		return appSubId;
	}

	public final void setAppSubId(String appSubId) {
		this.appSubId = appSubId;
	}

	public final String getAppToken() {
		return appToken;
	}

	public final void setAppToken(String appToken) {
		this.appToken = appToken;
	}

	public final String getApiId() {
		return apiId;
	}

	public final void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public final String getApiVersion() {
		return apiVersion;
	}

	public final void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public final String getTimeStamp() {
		return timeStamp;
	}

	public final void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public final String getSignMethod() {
		return signMethod;
	}

	public final void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}

	public final String getSign() {
		return sign;
	}

	public final void setSign(String sign) {
		this.sign = sign;
	}

	public final String getFormat() {
		return format;
	}

	public final void setFormat(String format) {
		this.format = format;
	}

	public final String getPartnerId() {
		return partnerId;
	}

	public final void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public final String getSysId() {
		return sysId;
	}

	public final void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public final String getAppPubId() {
		return appPubId;
	}

	public final void setAppPubId(String appPubId) {
		this.appPubId = appPubId;
	}

}
