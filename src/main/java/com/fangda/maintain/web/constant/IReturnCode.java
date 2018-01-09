/*
 * Copyright (c) 2015-2025 by HRT All rights reserved
 */
package com.fangda.maintain.web.constant;

import java.io.Serializable;

/**
 * @description 返回码，错误码的定义必须实现的接口。<br>
 *              《HRT应用集成技术规范》<br>
 * 				<br>
 *              响应码类型：E：错误，S：成功<br>
 * 				<br>
 *              信息类型：<br>
 *              M：表示数据信息/处理错误类<br>
 *              B：表示业务信息/处理错误类<br>
 *              S：表示系统信息/处理错误类<br>
 *              F：表示文件信息/处理错误类<br>
 *              D：表示数据库信息/处理错误类<br>
 *              Z：其他类别<br>
 * 
 * @version 1.0.0
 */
public interface IReturnCode extends Serializable {

	// T4是由应用集成组分配给积分系统的2位应用系统的标识
	String CURRENT_APP_SYSTEM_ID = "T5";
	
	public String getCode();

	public String getMsg();

	public boolean isSuccess();

}
