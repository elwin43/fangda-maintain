package com.fangda.maintain.web.service;

import com.fangda.maintain.web.model.Contract;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface ContractService {

	int delete(Long id);

	int insert(Contract contract);
	
	Long insertOrUpdateContract(Contract contract);
	
	int update(Contract contract);

	String findPageByParam(Map<String, Object> param);

	int countByParam(Map<String, Object> param);

	int updateStatus(Long id, String status, String auditComment, String auditBy, Date auditDate);
	
	Contract selectByPrimaryKey(Long id); 
	
	Contract getContractByNO(String contractNO);
	

}
