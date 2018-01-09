package com.fangda.maintain.web.service;

import com.fangda.maintain.web.model.Project;

import java.util.Map;


public interface ProjectService {

	int delete(Long id);

	int insert(Project contract);
	
	int update(Project contract);

	String findPageByParam(Map<String, Object> param);

	int countByParam(Map<String, Object> param);

	Project selectByPrimaryKey(Long id);
	

}
