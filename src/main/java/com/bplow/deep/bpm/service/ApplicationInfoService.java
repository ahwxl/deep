package com.bplow.deep.bpm.service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.base.pagination.Pagination;
import com.bplow.deep.bpm.domain.AutoAppInfo;

public interface ApplicationInfoService {
    
    public Page<AutoAppInfo> queryForList(AutoAppInfo autoAppInfo);

}
