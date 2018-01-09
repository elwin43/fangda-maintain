package com.fangda.maintain.web.service.impl;

import com.fangda.maintain.web.constant.ErrorCode;
import com.fangda.maintain.web.constant.MaintainConstant;
import com.fangda.maintain.web.constant.RedisConstant;
import com.fangda.maintain.web.domain.MccUserInfoDTO;
import com.fangda.maintain.web.exception.MaintainServiceException;
import com.fangda.maintain.web.model.MccUser;
import com.fangda.maintain.web.service.MccUserCacheService;
import com.fangda.maintain.web.service.MccUserService;
import com.fangda.maintain.web.utils.EnumBeanUtils;
import com.fangda.maintain.web.utils.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MccUserCacheServiceImpl implements MccUserCacheService {

	private static Logger logger = LoggerFactory.getLogger(MccUserCacheServiceImpl.class);

	@Autowired
	MccUserService mccUserService;

	@Autowired
	RedisUtils redisUtils;
	
	@Override
	public void validateIfTokenMatchesUserId(String userToken, Long userId) {
		MccUser mccUser = new MccUser();
		if(StringUtils.endsWith(userToken, "_phone")){
			getUserByTokenPhone(userToken);
		}else{
			getUserByToken(userToken);
		}
		if (mccUser != null && !mccUser.getUserId().equals(userId)) {
			throw new MaintainServiceException(ErrorCode.MEMBER_TOKEN_ERRRO.getCode(),
					ErrorCode.MEMBER_TOKEN_ERRRO.getMsg());
		}
	}

	// 作废！？后续优化，加到logout里
	@Override
	public void deleteUserCache(Long userId) {
		String redisMemberIdKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_USERNAME_USERID,
				String.valueOf(userId));
		if (redisUtils.exists(redisMemberIdKey)) {
			MccUser mccUser = redisUtils.getMemberSet(RedisConstant.REDIS_PREFIX_USERNAME_USERID,
					String.valueOf(userId), MccUser.class);
			redisUtils.del(RedisConstant.REDIS_PREFIX_USERNAME_USERID, String.valueOf(userId));
			String userName = null;
			if (mccUser != null) {
				userName = mccUser.getUserName();
				String redisMobileKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_USERID_USERINFO,
						userName);
				if (redisUtils.exists(redisMobileKey)) {
					redisUtils.del(RedisConstant.REDIS_PREFIX_USERID_USERINFO, userName);
				}
			}
		}

	}

	@Override
	public void delUserToken(String token) {
		String redisTokenKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_TOKEN_USERID, token);
		if (redisUtils.exists(redisTokenKey)) {
			Long memberId = redisUtils.getMemberSet(RedisConstant.REDIS_PREFIX_TOKEN_USERID, token,Long.class);
			// 1.del UserId_Token
			String redisMemberIdKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_USERID_TOKEN,
					String.valueOf(memberId));
			if (redisUtils.exists(redisMemberIdKey)) {
				redisUtils.del(RedisConstant.REDIS_PREFIX_USERID_TOKEN, String.valueOf(memberId));
			}
			// 2.del UserId_UserInfo
			String redisMemberIdKey2 = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_USERID_USERINFO,
					String.valueOf(memberId));
			if (redisUtils.exists(redisMemberIdKey2)) {
				redisUtils.del(RedisConstant.REDIS_PREFIX_USERID_USERINFO, String.valueOf(memberId));
			}
			// 3.del UserId_BaseInfo
			String redisMemberIdKey3 = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_USERID_BASEINFO,
					String.valueOf(memberId));
			if (redisUtils.exists(redisMemberIdKey3)) {
				redisUtils.del(RedisConstant.REDIS_PREFIX_USERID_BASEINFO, String.valueOf(memberId));
			}

			redisUtils.del(RedisConstant.REDIS_PREFIX_TOKEN_USERID, token);
		} else {
			throw new MaintainServiceException(ErrorCode.LOGOUT_ERROR.getCode(), ErrorCode.LOGOUT_ERROR.getMsg());
		}
	}

	private void refreshUserToken(Long userId, String userName, String token) {
		// 刷新 token
		redisUtils.expire(RedisConstant.REDIS_PREFIX_TOKEN_USERID, token,
				MaintainConstant.DEFAULT_FLUSH_EXPIRE);
		// 刷新 userId
		redisUtils.expire(RedisConstant.REDIS_PREFIX_USERID_TOKEN, String.valueOf(userId),
				MaintainConstant.DEFAULT_FLUSH_EXPIRE);
		redisUtils.expire(RedisConstant.REDIS_PREFIX_USERID_USERINFO, String.valueOf(userId),
				MaintainConstant.DEFAULT_FLUSH_EXPIRE);
		redisUtils.expire(RedisConstant.REDIS_PREFIX_USERID_BASEINFO, String.valueOf(userId),
				MaintainConstant.DEFAULT_FLUSH_EXPIRE);
		// 刷新 userName
		//redisUtils.expire(RedisConstant.REDIS_PREFIX_USERNAME_USERID, userName, MaintainConstant.DEFAULT_FLUSH_EXPIRE);
	}
	
//	private void refreshUserTokenPhone(Long userId, String userName, String token) {
//		// 刷新 token
//		redisUtils.expire(RedisConstant.REDIS_PREFIX_TOKEN_USERID_PHONE, token,
//				MaintainConstant.DEFAULT_FLUSH_EXPIRE);
//		// 刷新 userId
//		redisUtils.expire(RedisConstant.REDIS_PREFIX_USERID_TOKEN_PHONE, String.valueOf(userId),
//				MaintainConstant.DEFAULT_FLUSH_EXPIRE);
//		redisUtils.expire(RedisConstant.REDIS_PREFIX_USERID_USERINFO, String.valueOf(userId),
//				MaintainConstant.DEFAULT_FLUSH_EXPIRE);
//		redisUtils.expire(RedisConstant.REDIS_PREFIX_USERID_BASEINFO, String.valueOf(userId),
//				MaintainConstant.DEFAULT_FLUSH_EXPIRE);
//		// 刷新 userName
//		//redisUtils.expire(RedisConstant.REDIS_PREFIX_USERNAME_USERID, userName, MaintainConstant.DEFAULT_FLUSH_EXPIRE);
//	}

	@Override
	public void setUserToken(Long userId, String newToken) {
		if(StringUtils.endsWith(newToken, "_phone")){
			String redisUserIdKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_USERID_TOKEN_PHONE,
					String.valueOf(userId));
			// 存在就删除老的
			if (redisUtils.exists(redisUserIdKey)) {
				String oldToken = redisUtils.getMemberSet(RedisConstant.REDIS_PREFIX_USERID_TOKEN_PHONE,String.valueOf(userId));
				this.delUserTokenPhone(oldToken);
			}
			// new
			redisUtils.setMemberSet(RedisConstant.REDIS_PREFIX_USERID_TOKEN_PHONE, String.valueOf(userId), newToken,
					MaintainConstant.DEFAULT_EXPIRE_FOREVER);
			redisUtils.setMemberSet(RedisConstant.REDIS_PREFIX_TOKEN_USERID_PHONE, newToken, userId,
					MaintainConstant.DEFAULT_EXPIRE_FOREVER);
		}else{
			String redisUserIdKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_USERID_TOKEN,
					String.valueOf(userId));
			// 存在就删除老的
			if (redisUtils.exists(redisUserIdKey)) {
				String oldToken = redisUtils.getMemberSet(RedisConstant.REDIS_PREFIX_USERID_TOKEN,String.valueOf(userId));
				this.delUserToken(oldToken);
			}
			// new
			redisUtils.setMemberSet(RedisConstant.REDIS_PREFIX_USERID_TOKEN, String.valueOf(userId), newToken,
					MaintainConstant.DEFAULT_EXPIRE);
			redisUtils.setMemberSet(RedisConstant.REDIS_PREFIX_TOKEN_USERID, newToken, userId,
					MaintainConstant.DEFAULT_EXPIRE);
		}
	}
	
	@Override
	public void delUserTokenPhone(String token) {
		String redisTokenKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_TOKEN_USERID_PHONE, token);
		if (redisUtils.exists(redisTokenKey)) {
			Long memberId = redisUtils.getMemberSet(RedisConstant.REDIS_PREFIX_TOKEN_USERID_PHONE, token,Long.class);
			// 1.del UserId_Token
			String redisMemberIdKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_USERID_TOKEN_PHONE,
					String.valueOf(memberId));
			if (redisUtils.exists(redisMemberIdKey)) {
				redisUtils.del(RedisConstant.REDIS_PREFIX_USERID_TOKEN_PHONE, String.valueOf(memberId));
			}
			// 2.del UserId_UserInfo
			String redisMemberIdKey2 = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_USERID_USERINFO,
					String.valueOf(memberId));
			if (redisUtils.exists(redisMemberIdKey2)) {
				redisUtils.del(RedisConstant.REDIS_PREFIX_USERID_USERINFO, String.valueOf(memberId));
			}
			// 3.del UserId_BaseInfo
			String redisMemberIdKey3 = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_USERID_BASEINFO,
					String.valueOf(memberId));
			if (redisUtils.exists(redisMemberIdKey3)) {
				redisUtils.del(RedisConstant.REDIS_PREFIX_USERID_BASEINFO, String.valueOf(memberId));
			}

			redisUtils.del(RedisConstant.REDIS_PREFIX_TOKEN_USERID_PHONE, token);
		} else {
			throw new MaintainServiceException(ErrorCode.LOGOUT_ERROR.getCode(), ErrorCode.LOGOUT_ERROR.getMsg());
		}
	}

	// 用户信息
	private MccUser getUserInfo(Long userId) {
		String redisMemberIdKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_USERID_USERINFO,
				String.valueOf(userId));
		MccUser mccUser = null;
		if (redisUtils.exists(redisMemberIdKey)) {
			mccUser = redisUtils.getMemberSet(RedisConstant.REDIS_PREFIX_USERID_USERINFO,
					String.valueOf(userId), MccUser.class);
		} else {
			MccUser mccUser2 = new MccUser();
			MccUserInfoDTO mccUserInfoDTO = mccUserService.getUserBaseInfo(userId);
			EnumBeanUtils.beanCopy(mccUser,mccUserInfoDTO);
			if (mccUser != null) {
				redisUtils.setMemberSet(RedisConstant.REDIS_PREFIX_USERID_USERINFO, String.valueOf(userId),
						mccUser, MaintainConstant.DEFAULT_FLUSH_EXPIRE);
				//redisUtils.setMemberSet(RedisConstant.REDIS_PREFIX_USERNAME_USERID, mccUser.getUserName(),mccUser.getUserId(), MaintainConstant.DEFAULT_FLUSH_EXPIRE);
			}
		}
		return mccUser;
	}

	// 用户基本信息
	@Override
	public MccUserInfoDTO getUserBaseInfo(Long userId) {
		String redisMemberIdKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_USERID_BASEINFO,
				String.valueOf(userId));
		MccUserInfoDTO userInfoDTO = new MccUserInfoDTO();
		if (redisUtils.exists(redisMemberIdKey)) {
			userInfoDTO = redisUtils.getMemberSet(RedisConstant.REDIS_PREFIX_USERID_BASEINFO,
					String.valueOf(userId), MccUserInfoDTO.class);
		} else {
			userInfoDTO = mccUserService.getUserBaseInfo(userId);
			redisUtils.setMemberSet(RedisConstant.REDIS_PREFIX_USERID_BASEINFO, String.valueOf(userId),
					userInfoDTO, MaintainConstant.DEFAULT_FLUSH_EXPIRE);
		}
		return userInfoDTO;
	}

	@Override
	public MccUser getUserByUserId(Long userId) {
		return getUserInfo(userId);
	}

	// 暂无调用
	@Override
	public MccUser getUserByUserName(String userName) {
		String redisMobileKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_USERID_USERINFO,
				userName);
		MccUser mccUser = null;
		if (redisUtils.exists(redisMobileKey)) {
			Long userId = redisUtils.getMemberSet(RedisConstant.REDIS_PREFIX_USERID_USERINFO, userName,
					Long.class);
			mccUser = getUserInfo(userId);
		} else {
			MccUser mccUser2 = new MccUser();
			MccUserInfoDTO mccUserInfoDTO= mccUserService.getUserBaseInfoByName(userName);
			EnumBeanUtils.beanCopy(mccUser2,mccUserInfoDTO);
			if (mccUser != null) {
				redisUtils.setMemberSet(RedisConstant.REDIS_PREFIX_USERID_USERINFO, userName,mccUser.getUserId(), MaintainConstant.DEFAULT_FLUSH_EXPIRE);
				//redisUtils.setMemberSet(RedisConstant.REDIS_PREFIX_USERNAME_USERID,String.valueOf(mccUser.getUserId()), mccUser, MaintainConstant.DEFAULT_FLUSH_EXPIRE);
			}
		}
		return mccUser;
	}

	@Override
	public MccUser getUserByToken(String token) {
		String redisTokenKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_TOKEN_USERID, token);
		MccUser mccUser = null;
		if (redisUtils.exists(redisTokenKey)) {
			Long userId = redisUtils.getMemberSet(RedisConstant.REDIS_PREFIX_TOKEN_USERID, token,
					Long.class);
			mccUser = getUserInfo(userId);
			String userName = null;
			if (mccUser != null) {
				userName = mccUser.getUserName();
				refreshUserToken(userId, "", token);
			}
			return mccUser;
		}
		return mccUser;
	}
	
	@Override
	public MccUser getUserByTokenPhone(String token) {
		String redisTokenKey = redisUtils.getKeyString(RedisConstant.REDIS_PREFIX_TOKEN_USERID_PHONE, token);
		MccUser mccUser = null;
		if (redisUtils.exists(redisTokenKey)) {
			Long userId = redisUtils.getMemberSet(RedisConstant.REDIS_PREFIX_TOKEN_USERID_PHONE, token,
					Long.class);
			mccUser = getUserInfo(userId);
//			String userName = null;
//			if (mccUser != null) {
//				userName = mccUser.getUserName();
//				refreshUserTokenPhone(userId, "", token);
//			}
			return mccUser;
		}
		return mccUser;
	}

	@Override
	public MccUserInfoDTO getUserBaseInfoByToken(String token) {
		MccUserInfoDTO userInfoDTO = null;
		String key = "";
		if(token.endsWith("_phone")){
			key = RedisConstant.REDIS_PREFIX_TOKEN_USERID_PHONE;
		}else{
			key = RedisConstant.REDIS_PREFIX_TOKEN_USERID;
		}
		userInfoDTO = redisUtils.getMemberSet(key, token, MccUserInfoDTO.class);

		return userInfoDTO;
	}

}
