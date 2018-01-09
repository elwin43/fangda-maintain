package com.fangda.maintain.web.domain;

import java.io.Serializable;

/**
 * DESCRIPTION : 登录返回值 AUTHOR : zhanglei DATE : 2016/11/26.
 */
public class UserLoginDTO extends BaseInputDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8233518891671248020L;

	private Long userId;
	private String userName;
	private String opt;
	private String userType;
	private String token;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
