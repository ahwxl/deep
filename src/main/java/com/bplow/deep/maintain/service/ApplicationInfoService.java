package com.bplow.deep.maintain.service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.maintain.domain.AutoAppInfo;

public interface ApplicationInfoService {
    
    public Page<AutoAppInfo> queryForList(AutoAppInfo autoAppInfo);

}
