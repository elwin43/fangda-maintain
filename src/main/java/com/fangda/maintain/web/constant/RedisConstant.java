package com.fangda.maintain.web.constant;

public class RedisConstant {

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * OpenToken统一命名空间
	 */
	public final static String OPENTOKEN_NAME_SPACE = "OpenToken";

	/**
	 * 会员缓存通过OPENTOKEN查找 memberId
	 */
	public final static String REDIS_PREFIX_MEMBERID_OPENTOKEN = OPENTOKEN_NAME_SPACE + ": OpenToken_MemberId";

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 商家缓存统一命名空间
	 */
	public final static String MERCHANT_NAME_SPACE = "Merchant";

	// userId-> userInfo
	public final static String REDIS_PREFIX_USERID_USERINFO = MERCHANT_NAME_SPACE + ": UserId_UserInfo";

	// userId-> baseInfo
	public final static String REDIS_PREFIX_USERID_BASEINFO = MERCHANT_NAME_SPACE + ": UserId_BaseInfo";

	// username-> userId
	public final static String REDIS_PREFIX_USERNAME_USERID = MERCHANT_NAME_SPACE + ": UserName_UserId";

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Token统一命名空间
	 */
	public final static String TOKEN_NAME_SPACE = "TokenMC";
	
	public final static String TOKEN_NAME_SPACE_PHONE = "PHONE";

	// token-> userId
	public final static String REDIS_PREFIX_TOKEN_USERID = TOKEN_NAME_SPACE + ": Token_UserId ";
	
	public final static String REDIS_PREFIX_TOKEN_USERID_PHONE = TOKEN_NAME_SPACE + TOKEN_NAME_SPACE_PHONE + ": Token_UserId ";

	// userId-> token
	public final static String REDIS_PREFIX_USERID_TOKEN = TOKEN_NAME_SPACE + ": UserId_Token ";
	
	public final static String REDIS_PREFIX_USERID_TOKEN_PHONE = TOKEN_NAME_SPACE + TOKEN_NAME_SPACE_PHONE + ": UserId_Token ";

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 图片验证码统一命名空间
	 */
	public final static String IMAGE_VALIDATE_SPACE = "ImageValidate";

	// uuid-> image
	public final static String REDIS_PREFIX_UUID_IMAGE = IMAGE_VALIDATE_SPACE + "_Uuid_Image";

	///////////////////////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////////////////////////////////

}
