package com.fangda.maintain.web.domain;

import java.io.Serializable;
import java.util.Date;

/*
 * MccUserInfoDTO
 * 用于token取用户基本信息
 */
public class MccUserInfoDTO extends BaseInputDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5466606311125100561L;

	private Long userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 邮箱
	 */
	private String userEml;

	/**
	 * md5加密后的显示
	 */
	private String lgnPwd;

	/**
	 * 手机号前缀
	 */
	private String preMbl;

	/**
	 * 手机号
	 */
	private String userMbl;

	/**
	 * 姓
	 */
	private String firstName;

	/**
	 * 名
	 */
	private String lastName;

	/**
	 * 国家ID
	 */
	private Integer countryId;

	/**
	 * 城市ID
	 */
	private Integer cityId;

	/**
	 * 地址
	 */
	private String userAddress;

	/**
	 * 用户的个人网站地址
	 */
	private String userPersonalWebsite;

	/**
	 * 用户头像地址
	 */
	private String userAvatar;

	/**
	 * 性别
	 */
	private Short sex;

	/**
	 * 生日
	 */
	private Date birthday;

	/**
	 * 用户类型
	 */
	private String userType;

	/**
	 * 注册来源
	 */
	private Short regFrom;

	/**
	 * 用户状态，冻结等
	 */
	private Short lckStatus;

	/**
	 * 解锁时间
	 */
	private Date rlvTime;

	private String rpToken;

	/**
	 * reset password link token creation date
	 */
	private Date rpTokenCreatedAt;

	/**
	 * 是否删除
	 */
	private Short isDeleted;

	/**
	 * 乐观锁字段
	 */
	private Short optCounter;

	/**
	 * 激活码
	 */
	private String userActivationKey;

	/**
	 * 创建人
	 */
	private String createdBy;

	/**
	 * 修改人
	 */
	private String updatedBy;

	/**
	 * created at
	 */
	private Date createdAt;

	/**
	 * updated at
	 */
	private Date updatedAt;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEml() {
		return userEml;
	}

	public void setUserEml(String userEml) {
		this.userEml = userEml;
	}

	public String getLgnPwd() {
		return lgnPwd;
	}

	public void setLgnPwd(String lgnPwd) {
		this.lgnPwd = lgnPwd;
	}

	public String getPreMbl() {
		return preMbl;
	}

	public void setPreMbl(String preMbl) {
		this.preMbl = preMbl;
	}

	public String getUserMbl() {
		return userMbl;
	}

	public void setUserMbl(String userMbl) {
		this.userMbl = userMbl;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserPersonalWebsite() {
		return userPersonalWebsite;
	}

	public void setUserPersonalWebsite(String userPersonalWebsite) {
		this.userPersonalWebsite = userPersonalWebsite;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public Short getSex() {
		return sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Short getRegFrom() {
		return regFrom;
	}

	public void setRegFrom(Short regFrom) {
		this.regFrom = regFrom;
	}

	public Short getLckStatus() {
		return lckStatus;
	}

	public void setLckStatus(Short lckStatus) {
		this.lckStatus = lckStatus;
	}

	public Date getRlvTime() {
		return rlvTime;
	}

	public void setRlvTime(Date rlvTime) {
		this.rlvTime = rlvTime;
	}

	public String getRpToken() {
		return rpToken;
	}

	public void setRpToken(String rpToken) {
		this.rpToken = rpToken;
	}

	public Date getRpTokenCreatedAt() {
		return rpTokenCreatedAt;
	}

	public void setRpTokenCreatedAt(Date rpTokenCreatedAt) {
		this.rpTokenCreatedAt = rpTokenCreatedAt;
	}

	public Short getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Short isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Short getOptCounter() {
		return optCounter;
	}

	public void setOptCounter(Short optCounter) {
		this.optCounter = optCounter;
	}

	public String getUserActivationKey() {
		return userActivationKey;
	}

	public void setUserActivationKey(String userActivationKey) {
		this.userActivationKey = userActivationKey;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
