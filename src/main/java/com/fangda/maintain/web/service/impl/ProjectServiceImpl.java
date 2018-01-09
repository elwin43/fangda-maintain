package com.fangda.maintain.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.fangda.maintain.web.dao.ProjectDao;
import com.fangda.maintain.web.dao.mapper.ProjectMapper;
import com.fangda.maintain.web.domain.ProjectOutputDTO;
import com.fangda.maintain.web.exception.MaintainServiceException;
import com.fangda.maintain.web.model.Page;
import com.fangda.maintain.web.model.Project;
import com.fangda.maintain.web.service.ProjectService;
import com.fangda.maintain.web.utils.EnumBeanUtils;
import com.fangda.maintain.web.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
	private static Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
	private ProjectDao projectDao;
    
	@Override
	public int insert(Project project) {
		int result = projectMapper.insert(project);
		int id = 0;
		if(1 == result){
			id = project.getId().intValue();
		}
		return id;
	}

	@Override
	public int update(Project project) {
		return projectMapper.updateByPrimaryKey(project);
	}
	
	@Override
	public int delete(Long id) {
		return projectMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Project selectByPrimaryKey(Long id) {
		return projectMapper.selectByPrimaryKey(id);
	}


	@Override
	public String findPageByParam(Map<String, Object> param) {
		List<Project> list = projectDao.queryListByParam(param);
		List<ProjectOutputDTO> resultList = new ArrayList<ProjectOutputDTO>();
		for(Project obj : list){
			ProjectOutputDTO o = new ProjectOutputDTO();
			EnumBeanUtils.beanCopy(obj, o);
			Project project = this.selectByPrimaryKey(obj.getId());
			resultList.add(o);
		}
		try{
			Page<ProjectOutputDTO> page = new Page<ProjectOutputDTO>();
			// 设置page 参数
			// 设置page 参数
			if (param.get("pageNo") != null) {
				page.setPageNo(Integer.valueOf(param.get("pageNo").toString()));
			} else {
				page.setPageNo(1);
			}
			if (param.get("pageSize") != null) {
				page.setPageNo(Integer.valueOf(param.get("pageSize").toString()));
			}
			page.setTotalCount(projectDao.countByParam(param));
			page.setResult(resultList);
			return JSON.toJSONString(ResultUtil.crePageSucResult(page));
		}catch(MaintainServiceException e){
			return JSON.toJSONString(ResultUtil.creComErrorResult());
		}
		//return JSON.toJSONString(projectDao.queryListByParam(param));
	}

	@Override
	public int countByParam(Map<String,Object> param){
		return projectDao.countByParam(param);
	}

}
