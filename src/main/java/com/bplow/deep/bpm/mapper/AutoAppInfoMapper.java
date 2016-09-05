package com.bplow.deep.bpm.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.AutoAppInfo;

public interface AutoAppInfoMapper {
	
    int countByExample(AutoAppInfo example);

    int insert(AutoAppInfo record);
    
    int delete(Integer record);

    Page<AutoAppInfo> queryForPage(AutoAppInfo example);
    
    AutoAppInfo selectByPrimaryKey(Integer id);

    int update(AutoAppInfo record);
}