/*
 */
package com.fangda.maintain.web.constant;

/**
 * @description 积分Dubbo Service层所有可能返回给消费端的返回码。
 * 
 * @version 1.0.0
 */
public enum MaintainServiceReturnCode implements IReturnCode {

	// *******************************************************
	// ** 说明：
	// ** 遵循《HRT应用集成技术规范》进行定义。
	// ** Restful应用层的错误码在points-rest-api工程的单独文件中定义。
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
	ERR_SERVICE_INPUT_VALIDATION_REJECTED("M2001", "输入参数有误"),

	// B：表示业务信息/处理错误类

	// 注意！！不要改动或占用62030000这个错误码，因为APP前端会根据该错误码
	// 做逻辑判断
	// TODO: 20160928: 原有返回码62030000已改成统一集成规范的返回，需告诉apps前端开发

	
	// D：表示数据库信息/处理错误类
	ERR_DB_CONCURRENT_EXCEPTION("D2001", "数据库并发异常"),

	// S：表示系统信息/处理错误类
	ERR_SYSTEM_GENERAL_ERROR("S2001", "系统异常，请稍后再试"),

	ERR_NETWORK_ERROR("S2002", "网络连接异常"),

	ERR_DATABASE_ERROR("S2003", "数据库操作异常"),

	ERR_REMOTE_INVOKE_ERROR("S2004", "远程服务调用异常！"),

	ERR_SYSTEM_UPLOAD_FAIL("S2005", "文件上传失败！"),
	
	ERR_ALREADY_IN_AUDIT("S2006", "文件已经在审核中或者已经审核完成！"),
	
	ERR_MERCHANT_NOT_EXIST("S2007", "商户不存在！"),
	
	ERR_NO_VALID_POINTSRATE("S2008", "当前无可用积分比例！"),
	
	ERR_NO_VALID_CONTRACT("S2009", "合同不存在！"),
	
	ERR_NO_VALID_MERCHANT("S2010", "商户不存在！"),
	
	ERR_IMPORT_MERCHANT("S2011", "商户导入失败！"),
	
	ERR__CONTRACT_ALREADY_EXIST("S2012", "合同编号已存在！"),
	
	ERR_MERCHANT_NAME_EXIST("S2013", "商户名已存在！"),
	
	ERR_AUDIT_COMMENT_OUTSIZE("S2014", "审核意见不能超过500字符！"),
	
	ERR_STORE_ID_EXIST("S2015", "店铺id已经存在！"),
	
	ERR_STORE_NAME_EXIST("S2016", "店铺名称已经存在！"),
	
	ERR_FILE_SAVE_FAIL("S2017", "文件保存失败，请联系管理员！"),
	
	ERR_CONTRACT_DATE_INVALID("S2018", "合同失效日期必须大于生效日期！"),
	
	ERR_NO_FINANCE_INFO("S2019", "该商户没有财务信息！"),
	
	ERR_IN_AUDIT("S2020", "信息正在审核中！"),

	ERR_INTEGRATIONSYS_NOT_EXIST("S2021", "集成系统不存在！"),
	
	ERR_MODULE("S2022","导入失败，请下载模板，修改后重新上传"),
	
	ERR_ORG_NOT_EXIST("S2023","所选组织不存在！"),
	
	ERR_FILE_NOT_EXIST("S2024","文件不存在！"),
	
	ERR_PERIOD_CONTRACT_EXIST("S2025", "该有效期内已经存在已存在合同！"),
	
	ERR_DUPLICATE_VOUCHERCODE_EXIST("S2026", "该商户下已经存在该核销码！"),
	
	ERR_GENERATE_IMP_ERROR_RESULT("S2027", "生成导入错误失败！"),
	
	ERR_PERIOD_PREPAYMENT_MERCHANT_EXIST("S2029", "门店已为预付费卡商户门店，不能重复添加，请重新选择"),
	
	ERR_NO_PAYMENT_PWD("S2030", "对方未开通支付密码"),
	
	// Z：其他类别
	ERR_SERVICE_UNKNOWN_ERROR("Z9999", "系统繁忙"),

	ERR_APPLICATION_OPER_ERROR("B2003", "系统业务异常"),

	;

	// 积分Dubbo服务应用的错误码范围2000~9999
	private static final int MIN_ERR_NUMBER = 2000;
	private static final int MAX_ERR_NUMBER = 9999;
	private String code;
	private String msg;

	private MaintainServiceReturnCode(String errCodeRightPart, String returnDesc) {
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
			throw new IllegalArgumentException(
					"积分points-rest-api应用定义错误码后4位必须介于" + MIN_ERR_NUMBER + "~" + MAX_ERR_NUMBER + "这个范围");
		}

		this.code = "E" + CURRENT_APP_SYSTEM_ID + errCodeRightPart;
		this.msg = returnDesc;
	}

	private MaintainServiceReturnCode(String fullReturnCode, String returnDesc, boolean isSuccessReturnCode) {
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
