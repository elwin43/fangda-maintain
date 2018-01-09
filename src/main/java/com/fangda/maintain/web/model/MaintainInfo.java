package com.fangda.maintain.web.model;

import java.io.Serializable;
import java.util.Date;

public class MaintainInfo implements Serializable {
    private Long id;

    private Long projectid;

    private Long maintainId;

    private String pictureUrl;

    private String desc;

    private Date updateTime;

    private static final long serialVersionUID = 1L;


    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_maintain_info.id
     *
     * @param id the value for fd_maintain_info.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_maintain_info.projectId
     *
     * @return the value of fd_maintain_info.projectId
     *
     * @mbggenerated
     */
    public Long getProjectid() {
        return projectid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_maintain_info.projectId
     *
     * @param projectid the value for fd_maintain_info.projectId
     *
     * @mbggenerated
     */
    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_maintain_info.maintain_id
     *
     * @return the value of fd_maintain_info.maintain_id
     *
     * @mbggenerated
     */
    public Long getMaintainId() {
        return maintainId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_maintain_info.maintain_id
     *
     * @param maintainId the value for fd_maintain_info.maintain_id
     *
     * @mbggenerated
     */
    public void setMaintainId(Long maintainId) {
        this.maintainId = maintainId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_maintain_info.picture_url
     *
     * @return the value of fd_maintain_info.picture_url
     *
     * @mbggenerated
     */
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_maintain_info.picture_url
     *
     * @param pictureUrl the value for fd_maintain_info.picture_url
     *
     * @mbggenerated
     */
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_maintain_info.desc
     *
     * @return the value of fd_maintain_info.desc
     *
     * @mbggenerated
     */
    public String getDesc() {
        return desc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_maintain_info.desc
     *
     * @param desc the value for fd_maintain_info.desc
     *
     * @mbggenerated
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_maintain_info.update_time
     *
     * @return the value of fd_maintain_info.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_maintain_info.update_time
     *
     * @param updateTime the value for fd_maintain_info.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fd_maintain_info
     *
     * @mbggenerated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MaintainInfo other = (MaintainInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProjectid() == null ? other.getProjectid() == null : this.getProjectid().equals(other.getProjectid()))
            && (this.getMaintainId() == null ? other.getMaintainId() == null : this.getMaintainId().equals(other.getMaintainId()))
            && (this.getPictureUrl() == null ? other.getPictureUrl() == null : this.getPictureUrl().equals(other.getPictureUrl()))
            && (this.getDesc() == null ? other.getDesc() == null : this.getDesc().equals(other.getDesc()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fd_maintain_info
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProjectid() == null) ? 0 : getProjectid().hashCode());
        result = prime * result + ((getMaintainId() == null) ? 0 : getMaintainId().hashCode());
        result = prime * result + ((getPictureUrl() == null) ? 0 : getPictureUrl().hashCode());
        result = prime * result + ((getDesc() == null) ? 0 : getDesc().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}