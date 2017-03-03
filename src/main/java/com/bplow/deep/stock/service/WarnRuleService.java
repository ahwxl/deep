package com.bplow.deep.stock.service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkWarnRule;

public interface WarnRuleService {
    
    Page<SkWarnRule> queryWarnRules(SkWarnRule rule);
    
    SkWarnRule createWarnRule(SkWarnRule rule);
    
    int deleteWarnRule(SkWarnRule rule);
    
    SkWarnRule queryWarnRule(SkWarnRule rule);

}
