package com.fangda.maintain.web.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProjectInputDTO extends BaseInputDTO implements Serializable {

    private Long id;

    private String name;

    private List<MaintainInfoInputDTO> maintainInfos;

    private String location;

    private Date updateTime;

    private String createUser;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MaintainInfoInputDTO> getMaintainInfos() {
        return maintainInfos;
    }

    public void setMaintainInfos(List<MaintainInfoInputDTO> maintainInfos) {
        this.maintainInfos = maintainInfos;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

}