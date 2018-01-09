package com.fangda.maintain.web.service.impl;

import com.fangda.maintain.web.constant.ErrorCode;
import com.fangda.maintain.web.constant.MaintainConstant;
import com.fangda.maintain.web.constant.RedisConstant;
import com.fangda.maintain.web.domain.BaseOutputDTO;
import com.fangda.maintain.web.exception.MaintainServiceException;
import com.fangda.maintain.web.service.ImageValidateService;
import com.fangda.maintain.web.utils.RedisUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("imageValidateService")
public class ImageValidateServiceImpl implements ImageValidateService {

	private static final Logger LOGGER = LogManager.getLogger(ImageValidateServiceImpl.class);

	@Autowired
	RedisUtils redisUtils;

	@Override
	public void delImage(String uuid) {
		String redisKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_UUID_IMAGE, uuid);
		if (redisUtils.exists(redisKey)) {
			redisUtils.del(RedisConstant.REDIS_PREFIX_UUID_IMAGE, uuid);
		} else {
			throw new MaintainServiceException(ErrorCode.IMAGE_VALIDATE_EMPTY.getCode(),
					ErrorCode.IMAGE_VALIDATE_EMPTY.getMsg());
		}
	}

	@Override
	public void setImage(String uuid, String code) {
		String redisKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_UUID_IMAGE, uuid);
		if (redisUtils.exists(redisKey)) {
			redisUtils.del(RedisConstant.REDIS_PREFIX_UUID_IMAGE, uuid);
		}
		redisUtils.setMemberSet(RedisConstant.REDIS_PREFIX_UUID_IMAGE, uuid, code,
				MaintainConstant.IMAGE_VALIDATE_EXPIRE);
	}

	@Override
	public BaseOutputDTO getImage(String uuid) {
		BaseOutputDTO output = new BaseOutputDTO();
		String redisKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_UUID_IMAGE, uuid);
		try{
			if (redisUtils.exists(redisKey)) {
				String code = redisUtils.getMemberSet(RedisConstant.REDIS_PREFIX_UUID_IMAGE, uuid);
				output.setCode(ErrorCode.SYSTEM_SUCCESS.getCode());
				output.setMsg(ErrorCode.SYSTEM_SUCCESS.getMsg());
				output.setData("codeB64", code);
			} else {
				output.setCode(ErrorCode.IMAGE_VALIDATE_EMPTY.getCode());
				output.setMsg(ErrorCode.IMAGE_VALIDATE_EMPTY.getMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return output;
	}

}
