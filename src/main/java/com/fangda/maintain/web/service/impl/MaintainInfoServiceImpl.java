package com.fangda.maintain.web.service.impl;

import com.fangda.maintain.web.dao.MaintainInfoDao;
import com.fangda.maintain.web.dao.mapper.MaintainInfoMapper;
import com.fangda.maintain.web.domain.MaintainInfoOutputDTO;
import com.fangda.maintain.web.model.MaintainInfo;
import com.fangda.maintain.web.service.MaintainInfoService;
import com.fangda.maintain.web.utils.EnumBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import com.ibm.sc.core.utils.ResultUtil;

@Service("maintainInfoService")
public class MaintainInfoServiceImpl implements MaintainInfoService {
	private static Logger logger = LoggerFactory.getLogger(MaintainInfoServiceImpl.class);

    @Autowired
    private MaintainInfoMapper maintainInfoMapper;

    @Autowired
	private MaintainInfoDao maintainInfoDao;
    
	@Override
	public int insert(MaintainInfo maintainInfo) {
		int result = maintainInfoMapper.insert(maintainInfo);
		int id = 0;
		if(1 == result){
			id = maintainInfo.getId().intValue();
		}
		return id;
	}

	@Override
	public int update(MaintainInfo maintainInfo) {
		return maintainInfoMapper.updateByPrimaryKey(maintainInfo);
	}
	
	@Override
	public int delete(Long id) {
		return maintainInfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Long insertOrUpdateMaintainInfo(MaintainInfo maintainInfo) {
		MaintainInfo existMaintainInfo = null;
		if(null != maintainInfo.getId() && 0 != maintainInfo.getId()){
			existMaintainInfo = maintainInfoMapper.selectByPrimaryKey(maintainInfo.getId());
		}
		int result = 0;
		Long maintainInfoId = 0L;

		if(null != existMaintainInfo){
			maintainInfo.setId(existMaintainInfo.getId());
			maintainInfoMapper.updateByPrimaryKey(maintainInfo);
			maintainInfoId = existMaintainInfo.getId();
		}else{
			result = maintainInfoMapper.insert(maintainInfo);
			if(1 == result){
				maintainInfoId = maintainInfo.getId();
			}
		}
		return maintainInfoId;
	}

	@Override
	public MaintainInfo selectByPrimaryKey(Long id) {
		return maintainInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByProject(Long projectId, Long maintainId) {
		return maintainInfoDao.deleteByProject(projectId,maintainId);
	}

	@Override
	public List<MaintainInfoOutputDTO> findPageByParam(Map<String, Object> param) {
		List<MaintainInfo> list = maintainInfoDao.queryListByParam(param);
		List<MaintainInfoOutputDTO> resultList = new ArrayList<MaintainInfoOutputDTO>();
		for(MaintainInfo obj : list){
			MaintainInfoOutputDTO o = new MaintainInfoOutputDTO();
			EnumBeanUtils.beanCopy(obj, o);
			resultList.add(o);
		}

			return resultList;
	}

	@Override
	public int countByParam(Map<String,Object> param){
		return maintainInfoDao.countByParam(param);
	}

}
