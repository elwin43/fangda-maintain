package com.fangda.maintain.web.dao;

import com.fangda.maintain.web.model.MaintainInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MaintainInfoDao {
	
	List<MaintainInfo> queryListByParam(Map<String, Object> param);
	
	int countByParam(Map<String, Object> param);

	int deleteByProject(@Param(value="projectId") Long projectId, @Param(value="maintainId") Long maintainId);
	
}
