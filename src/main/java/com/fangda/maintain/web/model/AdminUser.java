package com.fangda.maintain.web.model;

import java.io.Serializable;
import java.util.Date;

public class AdminUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7911251538695725470L;
	private Date createAt;
    private String createBy;
	private Short isDeleted;
	private int isOpenFlag;
	private Date lastLogin;
	private Short lckStatus;
	private String lgnPwd;
	private int limitFree;
	private String roleCode;

	private String trueName;
	private Long userId;
	private String userMbl;
	private String userName;
	private String companyId;
	private String merchantNameZh;
	
	public String getMerchantNameZh() {
		return merchantNameZh;
	}
	public void setMerchantNameZh(String merchantNameZh) {
		this.merchantNameZh = merchantNameZh;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public String getCreateBy() {
		return createBy;
	}
	public Short getIsDeleted() {
		return isDeleted;
	}
	public int getIsOpenFlag() {
		return isOpenFlag;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public Short getLckStatus() {
		return lckStatus;
	}
	public String getLgnPwd() {
		return lgnPwd;
	}
	public int getLimitFree() {
		return limitFree;
	}
	public String getRoleCode() {
		return roleCode;
	}
	
	public String getTrueName() {
		return trueName;
	}
	public Long getUserId() {
		return userId;
	}
	public String getUserMbl() {
		return userMbl;
	}
	public String getUserName() {
		return userName;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
    public void setIsDeleted(Short isDeleted) {
		this.isDeleted = isDeleted;
	}
    public void setIsOpenFlag(int isOpenFlag) {
		this.isOpenFlag = isOpenFlag;
	}
    public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
    public void setLckStatus(Short lckStatus) {
		this.lckStatus = lckStatus;
	}
    public void setLgnPwd(String lgnPwd) {
		this.lgnPwd = lgnPwd;
	}
    public void setLimitFree(int limitFree) {
		this.limitFree = limitFree;
	}
    public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
    
    public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setUserMbl(String userMbl) {
		this.userMbl = userMbl;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}