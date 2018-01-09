package com.fangda.maintain.web.domain;

import java.util.List;

public class MaintainInfosInputDTO extends BaseOutputDTO {
    private List<MaintainInfoInputDTO> maintainInfoInputDTOList;

    public List<MaintainInfoInputDTO> getMaintainInfoInputDTOList() {
        return maintainInfoInputDTOList;
    }

    public void setMaintainInfoInputDTOList(List<MaintainInfoInputDTO> maintainInfoInputDTOList) {
        this.maintainInfoInputDTOList = maintainInfoInputDTOList;
    }
}
