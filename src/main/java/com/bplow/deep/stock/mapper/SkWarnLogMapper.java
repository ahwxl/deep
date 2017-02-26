package com.bplow.deep.stock.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkWarnLog;

public interface SkWarnLogMapper {
    Page<SkWarnLog> queryForPage(SkWarnLog record);

    SkWarnLog selectByPrimaryKey(String id);

    int update(SkWarnLog record);

    int insert(SkWarnLog record);

    int deleteByPrimaryKey(String id);
}