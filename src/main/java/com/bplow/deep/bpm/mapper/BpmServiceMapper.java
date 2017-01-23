package com.bplow.deep.bpm.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.ProcessInstanceInfo;

public interface BpmServiceMapper {
    
    Page<ProcessInstanceInfo> queryProcessInstanceItem(ProcessInstanceInfo process);
    
    Page<ProcessInstanceInfo> queryTasks(ProcessInstanceInfo process);

}
