/*
 * Copyright (c) 2015-2025 by HRT All rights reserved
 */
package com.fangda.maintain.web.constant;

/**
 * @description 积分REST层定义的返回码。
 * 
 * @version 1.0.0
 */
public enum MaintainRestReturnCode implements IReturnCode {

	// *******************************************************
	// ** 说明：
	// ** 遵循《HRT应用集成技术规范》进行定义。
	// ** Dubbo服务层的错误码在points-service-api工程的单独文件中定义。
	// **
	// ** 响应码类型：E：错误，S：成功
	// **
	// ** 信息类型：
	// ** M：表示数据信息/处理错误类
	// ** B：表示业务信息/处理错误类
	// ** S：表示系统信息/处理错误类
	// ** F：表示文件信息/处理错误类
	// ** D：表示数据库信息/处理错误类
	// ** Z：其他类别
	// *******************************************************

	/**
	 * 成功
	 */
	SUCCESS("S0A00000", "成功", true),

	// M：表示数据信息/处理错误类
	ERR_REST_INPUT_VALIDATION_REJECTED("M0001", "输入参数有误"),

	ERR_ILLEGAL_HTTP_REQUEST("M0002", "非法的HTTP请求"),

	// B：表示业务信息/处理错误类
	ERR_DUBBO_SERVICE_POINTS_ADJ("B0001", "积分调整异常，请检查"),

	// S：表示系统信息/处理错误类
	ERR_DUBBO_SERVICE_NETWORK_EXCEPTION("S0001", "内部网络异常访问核心业务失败"),

	ERR_DUBBO_SERVICE_WAIT_HANDLING_TIMEOUT("S0002", "等待核心业务处理超时"),

	ERR_DUBBO_SERVICE_FORBIDDEN_EXCEPTION("S0003", "核心业务不可用"),
	
	ERR_NETTY_SERVICE_CHECKMERCHANT_EXCEPTION("S0013", "当前商户与组织不符！"),

	ERR_DUBBO_SERVICE_UNKNOWN_EXCEPTION("S0004", "系统繁忙，核心业务暂时不可用"),
	
	ERR_EXCEL_FORMAT("S0014","excel读取失败"),

	// Z：其他类别
	ERR_IBM_FRAMEWORK_ERROR("Z0001", "IBM系统框架错误"),
	
	ERR_UNKNOWN_ERROR("Z1999", "系统繁忙，请稍后再试"),

	;

	// 积分Restful应用的错误码范围0001~1999
	private static final int MIN_ERR_NUMBER = 1;
	private static final int MAX_ERR_NUMBER = 1999;
	private String code;
	private String msg;

	private MaintainRestReturnCode(String errCodeRightPart, String returnDesc) {
		if (errCodeRightPart.length() != 5) {
			throw new IllegalArgumentException("根据《HRT应用集成技术规范》，返回码只有后5位是可定义的部分");
		}

		/**
		 * M：表示数据信息/处理错误类 <br>
		 * B：表示业务信息/处理错误类 <br>
		 * S：表示系统信息/处理错误类 <br>
		 * F：表示文件信息/处理错误类 <br>
		 * D：表示数据库信息/处理错误类 <br>
		 * Z：其他类别
		 */
		char errType = errCodeRightPart.charAt(0);
		switch (errType) {
		case 'M':
		case 'B':
		case 'S':
		case 'F':
		case 'D':
		case 'Z':
			break;
		default:
			throw new IllegalArgumentException("根据《HRT应用集成技术规范》，返回码倒数第5位为字母表示错误类型，目前有M、B、S、F、D、Z");
		}

		String errNumStr = errCodeRightPart.substring(1, 5);
		if (!errNumStr.matches("[0-9]{4}")) {
			throw new IllegalArgumentException("根据《HRT应用集成技术规范》，返回码后4位必须为数字");
		}
		int errNum = Integer.parseInt(errNumStr);
		if (errNum < MIN_ERR_NUMBER || errNum > MAX_ERR_NUMBER) {
			throw new IllegalArgumentException("积分points-rest-api应用定义错误码后4位必须介于0001~" + MAX_ERR_NUMBER + "这个范围");
		}

		this.code = "E" + CURRENT_APP_SYSTEM_ID + errCodeRightPart;
		this.msg = returnDesc;
	}

	private MaintainRestReturnCode(String fullReturnCode, String returnDesc, boolean isSuccessReturnCode) {
		this.code = fullReturnCode;
		this.msg = returnDesc;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public boolean isSuccess() {
		return SUCCESS.code.equals(code);
	}

}
