package com.fangda.maintain.web.dao;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public interface CompanyDao {

    int getPointMemberTotalLimitHistory(HashMap<String,Object> param);
}