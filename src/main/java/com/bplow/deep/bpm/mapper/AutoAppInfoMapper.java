package com.bplow.deep.bpm.mapper;

import java.util.List;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.AutoAppInfo;

public interface AutoAppInfoMapper extends Page<Object>{
	
    int countByExample(AutoAppInfo example);

    int insert(AutoAppInfo record);
    
    int delete(Integer record);

    List<AutoAppInfo> queryForPage(AutoAppInfo example);
    
    AutoAppInfo selectByPrimaryKey(Integer id);

    int update(AutoAppInfo record);
}