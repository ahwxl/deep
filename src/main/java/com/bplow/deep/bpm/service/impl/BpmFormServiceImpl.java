package com.bplow.deep.bpm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.BpmForm;
import com.bplow.deep.bpm.mapper.BpmFormMapper;
import com.bplow.deep.bpm.service.BpmFormService;

@Service
public class BpmFormServiceImpl implements BpmFormService{
    
    @Autowired
    private BpmFormMapper bmpFormMapper;

    @Override
    public Page<BpmForm> queryFormsForPage(BpmForm form) {
        
       Page<BpmForm> page= bmpFormMapper.queryForPage(form);
        
        return page;
    }

    @Override
    public BpmForm queryFormById(Integer formId) {
        
        BpmForm form = bmpFormMapper.selectByPrimaryKey(formId);
        
        return form;
    }

    @Override
    public void addForm(BpmForm form) {
        
        bmpFormMapper.insert(form);
        
    }

    @Override
    public void updateForm(BpmForm form) {
        
        bmpFormMapper.update(form);
    }

    @Override
    public void delForm(Integer formId) {
    
        bmpFormMapper.delete(formId);
    
    }

}
