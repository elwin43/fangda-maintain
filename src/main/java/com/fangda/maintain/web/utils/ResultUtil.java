package com.fangda.maintain.web.utils;


import com.fangda.maintain.web.constant.ErrorCode;
import com.fangda.maintain.web.model.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;





public class ResultUtil {
    
    @SuppressWarnings("rawtypes")
    public static Map<String, Object> crePageSucResult(Page page) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resCode", ErrorCode.SYSTEM_SUCCESS.getCode());
        resultMap.put("obj", page);
        return resultMap;
    }

    public static Map<String, Object> creObjSucResult(Object obj) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resCode", ErrorCode.SYSTEM_SUCCESS.getCode());
        resultMap.put("obj", obj);
        return resultMap;
    }

    public static Map<String, Object> creComSucResult() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resCode", ErrorCode.SYSTEM_SUCCESS.getCode());
        return resultMap;
    }
    
  
    @SuppressWarnings("rawtypes")
	public static Map<String, Object> creListSucResult(List list) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resCode", ErrorCode.SYSTEM_SUCCESS.getCode());
        Map<String, Object> listMap = new HashMap<String, Object>();
        listMap.put("list", list);
        resultMap.put("obj",listMap);
        return resultMap;
    }
    
    public static Map<String, Object> creComErrorResult(String message) {
		// TODO Auto-generated method stub
		if(message == null || message.equals("") ) {
			return creComErrorResult();
		} else {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("resCode", ErrorCode.DATA_EMPTY_ERROR.getCode());
			resultMap.put("msg", message);
			return resultMap;
		}
	}
    
    public static Map<String, Object> creComErrorResult() {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resCode", ErrorCode.DATA_EMPTY_ERROR.getCode());
		resultMap.put("msg", ErrorCode.DATA_EMPTY_ERROR.getMsg());
		return resultMap;
	}
}
