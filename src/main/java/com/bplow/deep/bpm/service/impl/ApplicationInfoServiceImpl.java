package com.bplow.deep.bpm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.pagination.Pagination;
import com.bplow.deep.bpm.domain.AutoAppInfo;
import com.bplow.deep.bpm.mapper.AutoAppInfoMapper;
import com.bplow.deep.bpm.service.ApplicationInfoService;

@Service
public class ApplicationInfoServiceImpl implements ApplicationInfoService{
    
    @Autowired
    private AutoAppInfoMapper autoAppInfoMapper;

    @Override
    public Pagination queryForList(AutoAppInfo autoAppInfo) {
        
        List<AutoAppInfo> list = autoAppInfoMapper.queryForPage(autoAppInfo);
        
        Pagination<AutoAppInfo> page = new Pagination<AutoAppInfo>();
        page.setDatas(list);
        page.setTotals(autoAppInfo.getTotalCount());
        page.setPageSize(autoAppInfo.getTotalCount());
        
        return page;
    }

}
