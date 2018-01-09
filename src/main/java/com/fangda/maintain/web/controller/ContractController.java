package com.fangda.maintain.web.controller;

import com.fangda.maintain.web.constant.ErrorCode;
import com.fangda.maintain.web.constant.MaintainRestReturnCode;
import com.fangda.maintain.web.domain.BaseOutputDTO;
import com.fangda.maintain.web.domain.ContractInputDTO;
import com.fangda.maintain.web.domain.JsonResponseObject;
import com.fangda.maintain.web.exception.MaintainServiceException;
import com.fangda.maintain.web.model.Contract;
import com.fangda.maintain.web.service.ContractService;
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
@RequestMapping("/contract")
public class ContractController extends AbstractRestController {
   
	private static final Logger logger = LogManager.getLogger(ContractController.class);

    @Autowired
	ContractService contractService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JsonResponseObject list(@RequestBody @Valid ContractInputDTO request, BindingResult result){
    	if (result.hasErrors()) {
			return createValidErrorMsgAsRestResponse(result);
		}
    	Map<String,Object> param = (Map<String,Object>) ConverterUtils.objectToMap(request);
    	JsonResponseObject res = new JsonResponseObject();
		String contracts = contractService.findPageByParam(param);
        res.setData(contracts);
        return res;
    }
    
    @RequestMapping(value = "/searchContract", method = RequestMethod.POST)
	public JsonResponseObject searchContract(@RequestBody @Valid ContractInputDTO request, BindingResult result) {
    	if (result.hasErrors()) {
			return createValidErrorMsgAsRestResponse(result);
		}
		try {
			Map<String,Object> param = (Map<String,Object>)ConverterUtils.objectToMap(request);
			String contractList = contractService.findPageByParam(param);
			/*List<ContractOutputDTO> contractOutputDTOList = new ArrayList<ContractOutputDTO>();
			for(Contract contract : contractList){
				ContractOutputDTO contractOutputDTO = new ContractOutputDTO();
				EnumBeanUtils.beanCopy(contractOutputDTO,contract);
				contractOutputDTOList.add(contractOutputDTO);
			}*/
			JsonResponseObject res = new JsonResponseObject();
			res.setData(contractList);
			/*if(null != contractOutputDTOList && contractOutputDTOList.size() > 0){
				res.setCode(contractOutputDTOList.get(0).getCode());
				res.setMsg(contractOutputDTOList.get(0).getMsg());
			}else{*/
				res.setCode(MaintainRestReturnCode.SUCCESS.getCode());
				res.setMsg(MaintainRestReturnCode.SUCCESS.getMsg());
			/*}*/
			return res;
		} catch (Throwable e) {
			logger.error("查询合同信息返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					e.getMessage());
			if( e instanceof MaintainServiceException){
				MaintainServiceException ex = (MaintainServiceException)e;
				return new JsonResponseObject(ex.getCode(),
						ex.getMessage());
			}
			return new JsonResponseObject(MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
					MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getMsg());
		}
	}
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public JsonResponseObject insert(@RequestBody ContractInputDTO req){
    	ContractInputDTO input = this.transfer2ContractInputDTO(req);
    	BaseOutputDTO out = new BaseOutputDTO();
		JsonResponseObject res = new JsonResponseObject();
    	try{
			Contract contract = new Contract();
			EnumBeanUtils.beanCopy(contract,input);
	        int i = contractService.insert(contract);
	        res.setData(i>0?"success":"fail");
	        if(i<=0){
	        	res.setCode(ErrorCode.SYSTEM_GENERAL_ERROR.getCode());
	        	res.setMsg(ErrorCode.SYSTEM_GENERAL_ERROR.getMsg());
	        }
    	} catch (Throwable e) {
			logger.error("查询促销活动列表返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
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
    public JsonResponseObject update(@RequestBody ContractInputDTO input){
		BaseOutputDTO out = new BaseOutputDTO();
		Contract contract = new Contract();
		JsonResponseObject res = new JsonResponseObject();
        try{
			EnumBeanUtils.beanCopy(contract,input);
			int i = contractService.update(contract);
			res.setData(i>0?"success":"fail");
			if(i<=0){
				res.setCode(ErrorCode.SYSTEM_GENERAL_ERROR.getCode());
				res.setMsg(ErrorCode.SYSTEM_GENERAL_ERROR.getMsg());
			}
		} catch (Throwable e) {
			logger.error("查询促销活动列表返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
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
	        int i = contractService.delete(orgId);
			res.setData(i>0?"success":"fail");
	        if(i<=0){
				res.setCode(ErrorCode.SYSTEM_GENERAL_ERROR.getCode());
				res.setMsg(ErrorCode.SYSTEM_GENERAL_ERROR.getMsg());
	        }
    	} catch (Throwable e) {
			logger.error("查询促销活动列表返回，code: {}, msg: {}", MaintainRestReturnCode.ERR_UNKNOWN_ERROR.getCode(),
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
    
    private ContractInputDTO transfer2ContractInputDTO(ContractInputDTO req) {
    	ContractInputDTO dto = new ContractInputDTO();
		BeanUtils.copyProperties(req, dto);
		return dto;
	}
    
}
