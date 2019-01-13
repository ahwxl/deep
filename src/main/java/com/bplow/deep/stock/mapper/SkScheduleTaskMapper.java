package com.bplow.deep.stock.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkScheduleTask;

public interface SkScheduleTaskMapper {
    Page<SkScheduleTask> queryForPage(SkScheduleTask record);

    SkScheduleTask selectByPrimaryKey(String id);

    int update(SkScheduleTask record);
    
    int updateStatus(SkScheduleTask record);

    int insert(SkScheduleTask record);

    int deleteByPrimaryKey(String id);
}