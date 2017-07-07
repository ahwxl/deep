package com.bplow.deep.bpm.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.BpmProcessDefinedSet;

public interface BpmProcessDefinedSetMapper {

    Page<BpmProcessDefinedSet> queryForPage(BpmProcessDefinedSet BpmProcessDefinedSet);

    BpmProcessDefinedSet selectByPrimaryKey(String processDefinedId);

    int update(BpmProcessDefinedSet record);

    int insert(BpmProcessDefinedSet record);

    int delete(String processDefinedId);
}