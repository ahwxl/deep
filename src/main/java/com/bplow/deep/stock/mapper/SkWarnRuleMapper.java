package com.bplow.deep.stock.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkWarnRule;

public interface SkWarnRuleMapper {
    Page<SkWarnRule> queryForPage(SkWarnRule record);

    SkWarnRule selectByPrimaryKey(String ruleId);

    int update(SkWarnRule record);

    int insert(SkWarnRule record);

    int deleteByPrimaryKey(String ruleId);
}