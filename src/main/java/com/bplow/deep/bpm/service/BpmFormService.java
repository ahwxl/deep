package com.bplow.deep.bpm.service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.BpmForm;

public interface BpmFormService {
    
    Page<BpmForm> queryFormsForPage(BpmForm form);
    
    BpmForm queryFormById(Integer formId);
    
    void addForm(BpmForm form);
    
    void updateForm(BpmForm form);
    
    void delForm(Integer formId);

}
