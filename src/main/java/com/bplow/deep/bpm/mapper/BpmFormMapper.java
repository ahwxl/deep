package com.bplow.deep.bpm.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.BpmForm;

public interface BpmFormMapper {

    Page<BpmForm> queryForPage(BpmForm BpmForm);

    BpmForm selectByPrimaryKey(Integer formId);

    int update(BpmForm record);

    int insert(BpmForm record);

    int delete(Integer formId);
}