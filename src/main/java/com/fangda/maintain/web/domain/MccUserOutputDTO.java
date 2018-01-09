package com.fangda.maintain.web.domain;

import java.util.Date;

public class MccUserOutputDTO extends BaseOutputDTO {
    private Long userId;

    private String userName;

    private String userEml;

    private String lgnPwd;

    private String userMbl;

    private String userAddress;

    private String userType;

    private Byte isDeleted;

    private Byte optCounter;

    private String updatedBy;

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

    public String getUserMbl() {
        return userMbl;
    }

    public void setUserMbl(String userMbl) {
        this.userMbl = userMbl;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Byte getOptCounter() {
        return optCounter;
    }

    public void setOptCounter(Byte optCounter) {
        this.optCounter = optCounter;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}