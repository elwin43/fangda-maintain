package com.fangda.maintain.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.fangda.maintain.web.dao.ContractDao;
import com.fangda.maintain.web.dao.mapper.ContractMapper;
import com.fangda.maintain.web.domain.ContractOutputDTO;
import com.fangda.maintain.web.exception.MaintainServiceException;
import com.fangda.maintain.web.model.Contract;
import com.fangda.maintain.web.model.Page;
import com.fangda.maintain.web.model.Project;
import com.fangda.maintain.web.service.ContractService;
import com.fangda.maintain.web.service.ProjectService;
import com.fangda.maintain.web.utils.EnumBeanUtils;
import com.fangda.maintain.web.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

//import com.ibm.sc.core.utils.ResultUtil;

@Service("contractService")
public class ContractServiceImpl implements ContractService {
	private static Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
	private ContractDao contractDao;

    @Autowired
	private ProjectService projectService;
    
	@Override
	public int insert(Contract contract) {
		contract.setDateUpdated(new Date());
		contract.setStatus("ENABLE");//新增默认enable
		int result = contractMapper.insert(contract);
		int id = 0;
		if(1 == result){
			id = contract.getId().intValue();
		}
		return id;
	}

	@Override
	public int update(Contract contract) {
		contract.setDateUpdated(new Date());
		return contractMapper.updateByPrimaryKey(contract);
	}
	
	@Override
	public int delete(Long id) {
		return contractMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateStatus(Long id, String status,String auditComment,String auditBy,Date auditDate) {
		
		return contractDao.updateStatus(id,status,auditComment,auditBy,auditDate);
	}

	@Override
	public Long insertOrUpdateContract(Contract contract) {
		Contract existContract = null;
		if(null != contract.getId() && 0 != contract.getId()){
			existContract = contractMapper.selectByPrimaryKey(contract.getId());
		}
		int result = 0;
		Long contractId = 0L;

		if(null != existContract){
			contract.setId(existContract.getId());
			contractMapper.updateByPrimaryKey(contract);
			contractId = existContract.getId();
		}else{
			result = contractMapper.insert(contract);
			if(1 == result){
				contractId = contract.getId();
			}
		}
		return contractId;
	}

	@Override
	public Contract selectByPrimaryKey(Long id) {
		return contractMapper.selectByPrimaryKey(id);
	}

	@Override
	public Contract getContractByNO(String contractNO) {
		return contractDao.getContractByNO(contractNO);
	}

	@Override
	public String findPageByParam(Map<String, Object> param) {
		List<Contract> list = contractDao.queryListByParam(param);
		List<ContractOutputDTO> resultList = new ArrayList<ContractOutputDTO>();
		for(Contract obj : list){
			ContractOutputDTO o = new ContractOutputDTO();
			EnumBeanUtils.beanCopy(obj, o);
			Project project = projectService.selectByPrimaryKey(obj.getProjectId());
			if(null != project){
				o.setContractName(project.getName());
			}
			resultList.add(o);
		}
		try{
			Page<ContractOutputDTO> page = new Page<ContractOutputDTO>();
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
			page.setTotalCount(contractDao.countByParam(param));
			page.setResult(resultList);
			return JSON.toJSONString(ResultUtil.crePageSucResult(page));
		}catch(MaintainServiceException e){
			return JSON.toJSONString(ResultUtil.creComErrorResult());
		}
		//return JSON.toJSONString(contractDao.queryListByParam(param));
	}

	@Override
	public int countByParam(Map<String,Object> param){
		return contractDao.countByParam(param);
	}

}
