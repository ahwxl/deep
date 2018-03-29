package com.bplow.deep.maintain.mapper;

import java.util.List;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.maintain.domain.AutoAppInfo;

public interface AutoAppInfoMapper extends Page<Object>{
	
    int countByExample(AutoAppInfo example);

    int insert(AutoAppInfo record);
    
    int delete(Integer record);

    Page<AutoAppInfo> queryForPage(AutoAppInfo example);
    
    AutoAppInfo selectByPrimaryKey(Integer id);

    int update(AutoAppInfo record);
}