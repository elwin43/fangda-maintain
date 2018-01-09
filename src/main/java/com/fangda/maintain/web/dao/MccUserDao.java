package com.fangda.maintain.web.dao;

import com.fangda.maintain.web.model.MccUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MccUserDao {

	List<MccUser> getAdminUserRelate(Map<String, Object> param);

    MccUser selectByUserName(@Param("userName") String userName);
}
