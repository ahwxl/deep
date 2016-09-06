package com.bplow.deep.bpm.service;

import com.bplow.deep.base.pagination.Pagination;
import com.bplow.deep.bpm.domain.AutoAppInfo;

public interface ApplicationInfoService {
    
    public Pagination queryForList(AutoAppInfo autoAppInfo);

}
