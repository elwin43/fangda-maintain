package com.fangda.maintain.web.controller;

import com.alibaba.fastjson.JSON;
import com.fangda.maintain.web.constant.ErrorCode;
import com.fangda.maintain.web.constant.MaintainRestReturnCode;
import com.fangda.maintain.web.domain.*;
import com.fangda.maintain.web.exception.MaintainServiceException;
import com.fangda.maintain.web.model.MccUser;
import com.fangda.maintain.web.service.MccUserService;
import com.fangda.maintain.web.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mortbay.jetty.security.Credential;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/reg")
public class UserRegController {
	private static final Logger LOGGER = LogManager.getLogger(UserRegController.class);

	@Autowired
	MccUserService mccUserServiceImpl;

	// B1.注册用户
	@RequestMapping(value = "/username", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public JsonResponseObject regByUserNameNew(@RequestBody MccUserInputDTO mccUserInputDTO) {
		// beanCopy
		//账号基本信息
		JsonResponseObject res = new JsonResponseObject();
		MccUserOutputDTO output = new MccUserOutputDTO();
		try{
			// 校验：是否存在用户名／邮箱／手机号和密码
			if (null == mccUserInputDTO || StringUtils.isBlank(mccUserInputDTO.getUserName()) || StringUtils.isBlank(mccUserInputDTO.getLgnPwd())) {
				res.setCode(ErrorCode.DATA_EMPTY_ERROR.getCode());
				res.setMsg(ErrorCode.DATA_EMPTY_ERROR.getMsg());
				return res;
			}

			//判断账号是否存在
			BaseOutputDTO output_isExit = new BaseOutputDTO();
			MccAdminUserInputDTO mccAdminUserInputDTO = new MccAdminUserInputDTO();
			mccAdminUserInputDTO.setUserName(mccUserInputDTO.getUserName());
			output_isExit = mccUserServiceImpl.isUserExist(mccUserInputDTO);

			boolean flag_isExit = false;
			BaseOutputDTO output_reg = new BaseOutputDTO();
			if(output_isExit != null && output_isExit.getData() != null){
				flag_isExit = (boolean)output_isExit.getData().get("flag");
				if(!flag_isExit){
					mccUserInputDTO.setToken(mccUserInputDTO.getToken());
					MccUser mccUser = new MccUser();
					BeanUtils.copyProperties(mccUserInputDTO,mccUser);
					mccUser.setIsDeleted(new Byte("0"));
					mccUser.setLgnPwd(Credential.MD5.digest(mccUser.getLgnPwd()).replace("MD5:", ""));
					output_reg = mccUserServiceImpl.saveUser(mccUser);
					BeanUtils.copyProperties(mccUser,output);
					LOGGER.info("用户注册日志定位"+ JSON.toJSON(output_reg));

					output.setData("output_reg", output_reg);

					String flag_reg = "";
					if(output_reg.getData() != null){
						flag_reg = output_reg.getData().get("insertFlag").toString();
						output.setData("flag_reg", flag_reg);
					}
				}
				output.setData("flag_isExit", flag_isExit);
			}
			res.setData(output);
		} catch (Throwable e) {
			LOGGER.error("注册用户返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					e.getMessage());
			if (e instanceof MaintainServiceException) {
				MaintainServiceException ex = (MaintainServiceException) e;
				return new JsonResponseObject(ex.getCode(), ex.getMessage());
			}
			return new JsonResponseObject(MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getMsg());
		}

		return res;
	}
	
	// update loginPass
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public JsonResponseObject updateUserInfo(@RequestBody MccUserInputDTO mccUserInputDTO) {
		// 初始化
		JsonResponseObject res = new JsonResponseObject();
		BaseOutputDTO output = new BaseOutputDTO();

		// 校验：是否存在用户名／邮箱／手机号和密码
		// 账号基本信息
		BaseOutputDTO output_user = new BaseOutputDTO();
		if (null == mccUserInputDTO || null == mccUserInputDTO.getUserId()) {
			output_user.setCode(ErrorCode.DATA_EMPTY_ERROR.getCode());
			output_user.setMsg(ErrorCode.DATA_EMPTY_ERROR.getMsg());
			output.setData("output_user", output_user);
			res.setData(output);
			return res;
		}
		MccUser mccUser = new MccUser();
		BeanUtils.copyProperties(mccUserInputDTO,mccUser);
		output_user = mccUserServiceImpl.updateUser(mccUser);

		if(output_user != null && output_user.getData() != null){
			boolean updateFlag_user = (boolean)output_user.getData().get("updateFlag");
			output.setData("updateFlag_user", updateFlag_user);
		}
		output.setData("output_user", output_user);
		res.setData(output);

		return res;
	}

}
