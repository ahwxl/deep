package com.bplow.deep.stock.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkWarnRule;
import com.bplow.deep.stock.mapper.SkWarnRuleMapper;
import com.bplow.deep.stock.service.WarnRuleService;

@Service
public class WarnRuleServiceImpl implements WarnRuleService{
    
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    SkWarnRuleMapper skWarnRuleMapper;

    @Override
    public Page<SkWarnRule> queryWarnRules(SkWarnRule rule) {
        
        Page<SkWarnRule> page = skWarnRuleMapper.queryForPage(rule);
        
        return page;
    }

    @Override
    public SkWarnRule createWarnRule(SkWarnRule rule) {
        
        skWarnRuleMapper.insert(rule);
        
        return null;
    }

    @Override
    public int deleteWarnRule(SkWarnRule rule) {
        
        skWarnRuleMapper.deleteByPrimaryKey(rule.getRuleId());
        
        return 0;
    }

    @Override
    public SkWarnRule queryWarnRule(SkWarnRule rule) {
        
        SkWarnRule warnRule = skWarnRuleMapper.selectByPrimaryKey(rule.getRuleId());
        
        return warnRule;
    }

}
