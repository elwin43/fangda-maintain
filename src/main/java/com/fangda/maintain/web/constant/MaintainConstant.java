package com.fangda.maintain.web.constant;


/**
 * 商家系统常量类
 * @author lenovo
 *
 */
public class MaintainConstant {
	
	/**demo*/
	public final static String DEMO = "demo";
	/***/
	public final static String MERCHANT_ADMIN_CENTER="T0000005";
	/***/
	public final static String MERCHANT_CENTER="T0000017";
	/**非会员手动积分批量号*/
	public final static Long POINTS_MANUAL_SINGLE_BATCH_NO = 999999999999999999L;
	
	
	/**
	 * 默认登陆超时时间
	 * 86400 * 7
	 */
	public final static int DEFAULT_EXPIRE = 3600;
	
	/**
	 * 小程序登录超时时间
	 */
	public final static int DEFAULT_EXPIRE_FOREVER = 43200;
	
	public final static int DEFAULT_FLUSH_EXPIRE = 3600;
	
	public final static int IMAGE_VALIDATE_EXPIRE = 6000;

	
	/**
	 * 一天秒数
	 */
	public final static int ONE_DAY_EXPIRE = 86400;

	public final static int HOUR_EXPIRE = 3600;
	
	/**
	 * 制卡商、卡号前段类型
	 */
	public final static String MERCHANT_FABRICATION = "FABRICATION";
	
	public final static String MERCHANT_CARDFOREPART = "CARDFOREPART";
	
	public final static String MERCHANT_FABRICATION_NAME = "制卡商";
	
	public final static String MERCHANT_CARDFOREPART_NAME = "卡号前段";

}
