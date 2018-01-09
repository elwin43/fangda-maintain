package com.fangda.maintain.web.domain;

public class UserLoginInputDTO extends UserLoginDTO {

	private static final long serialVersionUID = 6097289397811310983L;

	private String passWord;

	private String passWordOld;

	private String code;

	private String loginIP;
	
	private String appId;

	private String uuid;

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getPassWordOld() {
		return passWordOld;
	}

	public void setPassWordOld(String passWordOld) {
		this.passWordOld = passWordOld;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}