package com.bplow.deep.bpm.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.BpmProcessDefined;

public interface BpmProcessDefinedMapper {

    Page<BpmProcessDefined> queryForPage(BpmProcessDefined BpmProcessDefined);

    BpmProcessDefined selectByPrimaryKey(Long id);
    
    BpmProcessDefined queryBpmProcessDefined(BpmProcessDefined bpmProcessDefined);

    int update(BpmProcessDefined record);

    int insert(BpmProcessDefined record);

    int delete(Long id);
}