package com.fangda.maintain.web.domain;

import java.io.Serializable;

import com.fangda.maintain.web.constant.MaintainRestReturnCode;
import com.fangda.maintain.web.utils.TidManager;

public class JsonResponseObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String code;
	private String msg;
	private String transactionUuid;
	private Object data;
	
	public JsonResponseObject() {
		super();
		this.code = MaintainRestReturnCode.SUCCESS.getCode();
		this.msg = MaintainRestReturnCode.SUCCESS.getMsg();
		this.transactionUuid = TidManager.getTransactionUuid();
	}
	

	public JsonResponseObject(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
		this.transactionUuid = TidManager.getTransactionUuid();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTransactionUuid() {
		return transactionUuid;
	}

	public void setTransactionUuid(String transactionUuid) {
		this.transactionUuid = transactionUuid;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
