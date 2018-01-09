package com.fangda.maintain.web.controller;

import com.fangda.maintain.web.constant.ErrorCode;
import com.fangda.maintain.web.constant.MaintainRestReturnCode;
import com.fangda.maintain.web.domain.BaseOutputDTO;
import com.fangda.maintain.web.domain.JsonResponseObject;
import com.fangda.maintain.web.domain.ProjectInputDTO;
import com.fangda.maintain.web.exception.MaintainServiceException;
import com.fangda.maintain.web.model.Project;
import com.fangda.maintain.web.service.ProjectService;
import com.fangda.maintain.web.utils.ConverterUtils;
import com.fangda.maintain.web.utils.EnumBeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/project")
public class ProjectController extends AbstractRestController {
   
	private static final Logger logger = LogManager.getLogger(ProjectController.class);

    @Autowired
	ProjectService projectService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResponseObject list(@RequestBody @Valid ProjectInputDTO request, BindingResult result){
    	if (result.hasErrors()) {
			return createValidErrorMsgAsRestResponse(result);
		}
		JsonResponseObject res = new JsonResponseObject();
		try{
			Map<String,Object> param = (Map<String,Object>) ConverterUtils.objectToMap(request);
			String projects = projectService.findPageByParam(param);
			res.setData(projects);
		} catch (Throwable e) {
			logger.error("查询项目列表返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					e.getMessage());
			if (e instanceof MaintainServiceException) {
				MaintainServiceException ex = (MaintainServiceException) e;
				return new JsonResponseObject(ex.getCode(), ex.getMessage());
			}
			return new JsonResponseObject(MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getMsg());
		}
        return res;
    }
    
    @RequestMapping(value = "/searchProject", method = RequestMethod.POST)
	public JsonResponseObject searchProject(@RequestBody @Valid ProjectInputDTO request, BindingResult result) {
    	if (result.hasErrors()) {
			return createValidErrorMsgAsRestResponse(result);
		}
		JsonResponseObject res = new JsonResponseObject();
		try {
			Map<String,Object> param = (Map<String,Object>)ConverterUtils.objectToMap(request);
			String projectList = projectService.findPageByParam(param);
			res.setData(projectList);
			res.setCode(MaintainRestReturnCode.SUCCESS.getCode());
			res.setMsg(MaintainRestReturnCode.SUCCESS.getMsg());
			
		} catch (Throwable e) {
			logger.error("查询项目信息返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					e.getMessage());
			if( e instanceof MaintainServiceException){
				MaintainServiceException ex = (MaintainServiceException)e;
				return new JsonResponseObject(ex.getCode(),
						ex.getMessage());
			}
			return new JsonResponseObject(MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getMsg());
		}
		return res;
	}
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public JsonResponseObject insert(@RequestBody ProjectInputDTO req){
    	ProjectInputDTO input = this.transfer2ProjectInputDTO(req);
    	BaseOutputDTO out = new BaseOutputDTO();
		JsonResponseObject res = new JsonResponseObject();
    	try{
			Project project = new Project();
			EnumBeanUtils.beanCopy(project,input);
	        int i = projectService.insert(project);
	        res.setData(i>0?"success":"fail");
	        if(i<=0){
	        	res.setCode(ErrorCode.SYSTEM_GENERAL_ERROR.getCode());
	        	res.setMsg(ErrorCode.SYSTEM_GENERAL_ERROR.getMsg());
	        }
    	} catch (Throwable e) {
			logger.error("查询项目列表返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					e.getMessage());
			if (e instanceof MaintainServiceException) {
				MaintainServiceException ex = (MaintainServiceException) e;
				return new JsonResponseObject(ex.getCode(), ex.getMessage());
			}
			return new JsonResponseObject(MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getMsg());
		}
        return res;
    }

	private void handleException(BaseOutputDTO out, Exception e) {
		if( e instanceof MaintainServiceException){
            MaintainServiceException ex = (MaintainServiceException)e;
            out.setCode(ex.getCode());
            out.setMsg(ex.getMessage());
        }else{
            out.setCode(ErrorCode.SYSTEM_GENERAL_ERROR.getCode());
            out.setMsg(e.getMessage());
        }
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResponseObject update(@RequestBody ProjectInputDTO input){
		BaseOutputDTO out = new BaseOutputDTO();
		Project project = new Project();
		JsonResponseObject res = new JsonResponseObject();
        try{
			EnumBeanUtils.beanCopy(project,input);
			int i = projectService.update(project);
			res.setData(i>0?"success":"fail");
			if(i<=0){
				res.setCode(ErrorCode.SYSTEM_GENERAL_ERROR.getCode());
				res.setMsg(ErrorCode.SYSTEM_GENERAL_ERROR.getMsg());
			}
		} catch (Throwable e) {
			logger.error("查询项目列表返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					e.getMessage());
			if (e instanceof MaintainServiceException) {
				MaintainServiceException ex = (MaintainServiceException) e;
				return new JsonResponseObject(ex.getCode(), ex.getMessage());
			}
			return new JsonResponseObject(MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getMsg());
		}
        return res;
    }
    
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JsonResponseObject delete(@RequestBody Map<String,Object> param){
        
    	BaseOutputDTO out = new BaseOutputDTO();
		JsonResponseObject res = new JsonResponseObject();
    	try{
	    	Long orgId = Long.valueOf((String)param.get("orgId"));
	        int i = projectService.delete(orgId);
			res.setData(i>0?"success":"fail");
	        if(i<=0){
				res.setCode(ErrorCode.SYSTEM_GENERAL_ERROR.getCode());
				res.setMsg(ErrorCode.SYSTEM_GENERAL_ERROR.getMsg());
	        }
    	} catch (Throwable e) {
			logger.error("查询项目列表返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					e.getMessage());
			if (e instanceof MaintainServiceException) {
				MaintainServiceException ex = (MaintainServiceException) e;
				return new JsonResponseObject(ex.getCode(), ex.getMessage());
			}
			return new JsonResponseObject(MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getMsg());
		}
        return res;
    }
    
    private ProjectInputDTO transfer2ProjectInputDTO(ProjectInputDTO req) {
		ProjectInputDTO dto = new ProjectInputDTO();
		BeanUtils.copyProperties(req, dto);
		return dto;
	}
    
}
