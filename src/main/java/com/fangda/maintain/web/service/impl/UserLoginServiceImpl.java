package com.fangda.maintain.web.service.impl;

import com.fangda.maintain.web.constant.ErrorCode;
import com.fangda.maintain.web.domain.BaseInputDTO;
import com.fangda.maintain.web.domain.BaseOutputDTO;
import com.fangda.maintain.web.domain.UserLoginInputDTO;
import com.fangda.maintain.web.exception.MaintainServiceException;
import com.fangda.maintain.web.service.ImageValidateService;
import com.fangda.maintain.web.service.MccUserService;
import com.fangda.maintain.web.service.UserLoginService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {

	private static Logger LOGGER = LoggerFactory.getLogger(UserLoginServiceImpl.class);

	@Autowired
	MccUserService mccUserService;

	@Autowired
	ImageValidateService imageValidateService;

	@Override
	public BaseOutputDTO delImage(String uuid) {
		LOGGER.info("userLoginFacade -> delImage");
		if (StringUtils.isBlank(uuid)) {
			throw new MaintainServiceException(ErrorCode.DATA_EMPTY_ERROR.getCode(), ErrorCode.DATA_EMPTY_ERROR.getMsg());
		}
		imageValidateService.delImage(uuid);
		BaseOutputDTO output = new BaseOutputDTO();
		output.setData("delImage", "ok");
		return output;
	}

	@Override
	public BaseOutputDTO setImage(String uuid, String code) {
		LOGGER.info("userLoginFacade -> setImage");
		if (StringUtils.isBlank(uuid) || StringUtils.isBlank(code)) {
			throw new MaintainServiceException(ErrorCode.DATA_EMPTY_ERROR.getCode(), ErrorCode.DATA_EMPTY_ERROR.getMsg());
		}
		imageValidateService.setImage(uuid, code);
		BaseOutputDTO output = new BaseOutputDTO();
		output.setData("setImage", "ok");
		return output;
	}

	@Override
	public BaseOutputDTO getImage(String uuid) {
		return null;
	}

	@Override
	public BaseOutputDTO userLogin(UserLoginInputDTO input) {
		return null;
	}

	@Override
	public BaseOutputDTO currentUserLogout(BaseInputDTO input) {
		return null;
	}

	@Override
	public BaseOutputDTO checkUserLogined(BaseInputDTO input) {
		return null;
	}

	@Override
	public BaseOutputDTO getUserInfoByToken(BaseInputDTO input) {
		return null;
	}

	@Override
	public BaseOutputDTO getUserBaseInfoByToken(String token) {
		return null;
	}
}
