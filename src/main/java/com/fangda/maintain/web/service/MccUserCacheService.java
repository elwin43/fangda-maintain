package com.fangda.maintain.web.service;

import com.fangda.maintain.web.domain.MccUserInfoDTO;
import com.fangda.maintain.web.model.MccUser;

public interface MccUserCacheService {
	/**
	 * 校验token和memberId是否有效匹配，如果校验不通过，则抛出异常ServiceValidationRejectedException。
	 * void
	 */
	public void validateIfTokenMatchesUserId(String userToken, Long userId);

	public void deleteUserCache(Long userId);

	public void delUserToken(String token);
	
	public void delUserTokenPhone(String tocken);

	public void setUserToken(Long userId, String newToken);
	
	public MccUser getUserByUserId(Long userId);

	public MccUser getUserByUserName(String userName);

	public MccUser getUserByToken(String token);

	public MccUserInfoDTO getUserBaseInfoByToken(String token);
	
	public MccUser getUserByTokenPhone(String token);

	/*
	 * 获取用户基本信息，走redis
	 * 注意：若不走缓存，使用MccUserService.getUserInfoSimple(Long userId);
	 */
	public MccUserInfoDTO getUserBaseInfo(Long userId);

}
