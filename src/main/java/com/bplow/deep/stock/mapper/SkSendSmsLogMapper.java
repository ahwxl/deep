package com.bplow.deep.stock.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkSendSmsLog;

public interface SkSendSmsLogMapper {
    Page<SkSendSmsLog> queryForPage(SkSendSmsLog record);

    SkSendSmsLog selectByPrimaryKey(String smsId);

    int update(SkSendSmsLog record);

    int insert(SkSendSmsLog record);

    int deleteByPrimaryKey(String smsId);
}