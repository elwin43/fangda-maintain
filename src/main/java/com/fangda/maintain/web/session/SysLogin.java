package com.fangda.maintain.web.session;

import com.fangda.maintain.web.constant.RedisConstant;
import com.fangda.maintain.web.domain.MccUserInfoDTO;
import com.fangda.maintain.web.utils.RedisUtils;
import org.apache.commons.lang.StringUtils;

public class SysLogin {
	public static final String SESSION_KEY="1";
	public static final String SYSTEM_KEY="1";
	
	public static MccUserInfoDTO getUserInfo(RedisUtils redisUtils, String userToken){
		MccUserInfoDTO mccUserInfo = null;
		if(StringUtils.endsWith(userToken, "_phone")){
			mccUserInfo = redisUtils.getMemberSet(RedisConstant.REDIS_PREFIX_TOKEN_USERID_PHONE, userToken, MccUserInfoDTO.class);
		}else{
			mccUserInfo = redisUtils.getMemberSet(RedisConstant.REDIS_PREFIX_TOKEN_USERID, userToken, MccUserInfoDTO.class);
		}
		return mccUserInfo;
	}
}
