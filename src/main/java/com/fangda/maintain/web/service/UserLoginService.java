package com.fangda.maintain.web.service;


import com.fangda.maintain.web.domain.BaseInputDTO;
import com.fangda.maintain.web.domain.BaseOutputDTO;
import com.fangda.maintain.web.domain.UserLoginInputDTO;

public interface UserLoginService {
	
	/*
	 * ========== 图片验证码相关 ==========
	 */
	public BaseOutputDTO delImage(String uuid);

	public BaseOutputDTO setImage(String uuid, String code);

	public BaseOutputDTO getImage(String uuid);

	/*
	 * ========== 登录相关 ==========
	 */
	// 登录by用户名密码
	BaseOutputDTO userLogin(UserLoginInputDTO input);

	// 登出
	BaseOutputDTO currentUserLogout(BaseInputDTO input);

	// 判断用户是否已经登录
	BaseOutputDTO checkUserLogined(BaseInputDTO input);

	// 通过token找用户
	BaseOutputDTO getUserInfoByToken(BaseInputDTO input);

	// 通过token找用户及所有信息（包括：基本信息、组织、角色）
	BaseOutputDTO getUserBaseInfoByToken(String token);

}
