package com.fangda.maintain.web.dao;

import com.fangda.maintain.web.model.Project;

import java.util.List;
import java.util.Map;

public interface ProjectDao {
	
	List<Project> queryListByParam(Map<String, Object> param);
	
	int countByParam(Map<String, Object> param);
	
}
