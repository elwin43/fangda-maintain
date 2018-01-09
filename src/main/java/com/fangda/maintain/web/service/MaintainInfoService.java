package com.fangda.maintain.web.service;

import com.fangda.maintain.web.domain.MaintainInfoOutputDTO;
import com.fangda.maintain.web.model.MaintainInfo;

import java.util.List;
import java.util.Map;


public interface MaintainInfoService {

	int delete(Long id);

	int insert(MaintainInfo maintainInfo);
	
	Long insertOrUpdateMaintainInfo(MaintainInfo maintainInfo);
	
	int update(MaintainInfo maintainInfo);

	List<MaintainInfoOutputDTO> findPageByParam(Map<String, Object> param);

	int countByParam(Map<String, Object> param);

	MaintainInfo selectByPrimaryKey(Long id);

	int deleteByProject(Long projectId, Long maintainId);

}
