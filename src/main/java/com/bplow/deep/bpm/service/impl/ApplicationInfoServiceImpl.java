package com.bplow.deep.bpm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.base.pagination.Pagination;
import com.bplow.deep.bpm.domain.AutoAppInfo;
import com.bplow.deep.bpm.mapper.AutoAppInfoMapper;
import com.bplow.deep.bpm.service.ApplicationInfoService;

@Service
public class ApplicationInfoServiceImpl implements ApplicationInfoService{
    
    @Autowired
    private AutoAppInfoMapper autoAppInfoMapper;

    @Override
    public Page<AutoAppInfo> queryForList(AutoAppInfo autoAppInfo) {
        
        Page<AutoAppInfo> page = autoAppInfoMapper.queryForPage(autoAppInfo);
        
        return page;
    }

}
