package com.bplow.deep.stock.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkDaySwrich;

public interface SkDaySwrichMapper {
    Page<SkDaySwrich> queryForPage(SkDaySwrich record);

    SkDaySwrich selectByPrimaryKey(String id);

    int update(SkDaySwrich record);

    int insert(SkDaySwrich record);

    int deleteByPrimaryKey(String id);
}