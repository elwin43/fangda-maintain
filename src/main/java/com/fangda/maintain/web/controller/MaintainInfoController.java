package com.fangda.maintain.web.controller;

import com.fangda.maintain.web.constant.ErrorCode;
import com.fangda.maintain.web.constant.MaintainRestReturnCode;
import com.fangda.maintain.web.domain.*;
import com.fangda.maintain.web.exception.MaintainServiceException;
import com.fangda.maintain.web.model.MaintainInfo;
import com.fangda.maintain.web.service.MaintainInfoService;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/maintainInfo")
public class MaintainInfoController extends AbstractRestController {
   
	private static final Logger logger = LogManager.getLogger(MaintainInfoController.class);

    @Autowired
	MaintainInfoService maintainInfoService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResponseObject list(@RequestBody @Valid MaintainInfoInputDTO request, BindingResult result){
    	if (result.hasErrors()) {
			return createValidErrorMsgAsRestResponse(result);
		}
		JsonResponseObject res = new JsonResponseObject();
		try{
			Map<String,Object> param = (Map<String,Object>) ConverterUtils.objectToMap(request);
			List<MaintainInfoOutputDTO> maintainInfos = maintainInfoService.findPageByParam(param);
			res.setData(maintainInfos);
		} catch (Throwable e) {
			logger.error("查询维护列表返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
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
    
    @RequestMapping(value = "/searchMaintainInfo", method = RequestMethod.POST)
	public JsonResponseObject searchMaintainInfo(@RequestBody @Valid MaintainInfoInputDTO request, BindingResult result) {
    	if (result.hasErrors()) {
			return createValidErrorMsgAsRestResponse(result);
		}
		JsonResponseObject res = new JsonResponseObject();
		try {
			Map<String,Object> param = (Map<String,Object>)ConverterUtils.objectToMap(request);
			List<MaintainInfoOutputDTO> maintainInfoList = maintainInfoService.findPageByParam(param);
			res.setData(maintainInfoList);
			res.setCode(MaintainRestReturnCode.SUCCESS.getCode());
			res.setMsg(MaintainRestReturnCode.SUCCESS.getMsg());
		} catch (Throwable e) {
			logger.error("查询维护信息返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
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
    public JsonResponseObject insert(@RequestBody MaintainInfosInputDTO req){
		BaseOutputDTO out = new BaseOutputDTO();
		JsonResponseObject res = new JsonResponseObject();
		List<MaintainInfoInputDTO> maintainInfosInputDTO;

    	try{
			if(null != req && null != req.getMaintainInfoInputDTOList() && req.getMaintainInfoInputDTOList().size() > 0 ){
				maintainInfosInputDTO = req.getMaintainInfoInputDTOList();
				for(MaintainInfoInputDTO maintainInfoInputDTO : maintainInfosInputDTO){
					MaintainInfoInputDTO input = this.transfer2MaintainInfoInputDTO(maintainInfoInputDTO);
					MaintainInfo maintainInfo = new MaintainInfo();
					EnumBeanUtils.beanCopy(maintainInfo,input);
					maintainInfoService.insert(maintainInfo);
				}
			}
	        res.setData(req);
    	} catch (Throwable e) {
			logger.error("新增维护信息返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
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

	@RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResponseObject update(@RequestBody MaintainInfoInputDTO input){
		BaseOutputDTO out = new BaseOutputDTO();
		MaintainInfo maintainInfo = new MaintainInfo();
		JsonResponseObject res = new JsonResponseObject();
        try{
			EnumBeanUtils.beanCopy(maintainInfo,input);
			int i = maintainInfoService.update(maintainInfo);
			res.setData(i>0?"success":"fail");
			if(i<=0){
				res.setCode(ErrorCode.SYSTEM_GENERAL_ERROR.getCode());
				res.setMsg(ErrorCode.SYSTEM_GENERAL_ERROR.getMsg());
			}
		} catch (Throwable e) {
			logger.error("查询维护列表返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
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
	        int i = maintainInfoService.delete(orgId);
			res.setData(i>0?"success":"fail");
	        if(i<=0){
				res.setCode(ErrorCode.SYSTEM_GENERAL_ERROR.getCode());
				res.setMsg(ErrorCode.SYSTEM_GENERAL_ERROR.getMsg());
	        }
    	} catch (Throwable e) {
			logger.error("查询维护列表返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
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
    
    private MaintainInfoInputDTO transfer2MaintainInfoInputDTO(MaintainInfoInputDTO req) {
		MaintainInfoInputDTO dto = new MaintainInfoInputDTO();
		BeanUtils.copyProperties(req, dto);
		return dto;
	}
    
}
