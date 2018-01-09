package com.fangda.maintain.web.service;

import com.fangda.maintain.web.domain.BaseOutputDTO;
import com.fangda.maintain.web.domain.MccUserInfoDTO;
import com.fangda.maintain.web.domain.MccUserInputDTO;
import com.fangda.maintain.web.domain.UserLoginInputDTO;
import com.fangda.maintain.web.model.MccUser;

import java.util.Map;

public interface MccUserService{

	boolean isUserExist(Long userId);

	BaseOutputDTO isUserExist(MccUserInputDTO input);

	// 查询用户的基本信息，simple
	MccUserInfoDTO getUserBaseInfo(Long userId);

	// 根据名称查询用户基本信息
	MccUserInfoDTO getUserBaseInfoByName(String userName);

	// 重置登录密码
	BaseOutputDTO resetPassword(Long userId);

	// 修改登录密码
	BaseOutputDTO updatePassword(UserLoginInputDTO input);

	// 更改用户状态：冻结&解冻
	BaseOutputDTO updateUserStatus(Long userId, byte status);

	// 修改用户资料
	BaseOutputDTO updateUser(MccUser input);

	//新增用户
	BaseOutputDTO saveUser(MccUser input);


	int countByParam(Map<String, Object> param);


}
