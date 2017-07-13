package com.bplow.deep.bpm.service;

import java.util.List;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.BpmForm;

public interface BpmFormService {
    
    Page<BpmForm> queryFormsForPage(BpmForm form);
    
    List<BpmForm> queryForms(BpmForm form);
    
    BpmForm queryFormById(Integer formId);
    
    void addForm(BpmForm form);
    
    void updateForm(BpmForm form);
    
    void delForm(Integer formId);

}
