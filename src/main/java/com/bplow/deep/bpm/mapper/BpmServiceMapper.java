package com.bplow.deep.bpm.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.bpm.domain.ProcessInstanceInfo;

public interface BpmServiceMapper {
    
    Page<ProcessInstanceInfo> queryProcessInstanceItemForPage(ProcessInstanceInfo process);
    
    Page<ProcessInstanceInfo> queryTasksForPage(ProcessInstanceInfo process);

}
