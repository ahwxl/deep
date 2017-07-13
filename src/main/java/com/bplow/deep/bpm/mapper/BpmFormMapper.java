package com.bplow.deep.bpm.mapper;

import java.util.List;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.BpmForm;

public interface BpmFormMapper {

    Page<BpmForm> queryForPage(BpmForm bpmForm);
    
    List<BpmForm> queryForms(BpmForm bpmForm);

    BpmForm selectByPrimaryKey(Integer formId);

    int update(BpmForm record);

    int insert(BpmForm record);

    int delete(Integer formId);
}