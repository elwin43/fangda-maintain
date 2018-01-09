package com.fangda.maintain.web.dao;

import com.fangda.maintain.web.model.Contract;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ContractDao {
	
	List<Contract> queryListByParam(Map<String, Object> param);
	
	int countByParam(Map<String, Object> param);
	
	int softDeleteByPrimaryKey(Long id);
	
	Contract getByOrgName(String orgZhname);
	
	int updateStatus(Long id, String status, String auditComment, String auditBy, Date auditDate);
	
	List<Contract> getSpeContractByMerId(Map<String, Object> param);
	
	Contract getContractByNO(String contractNO);
	
}
