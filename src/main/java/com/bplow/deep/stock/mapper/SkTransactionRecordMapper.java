package com.bplow.deep.stock.mapper;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkTransactionRecord;

public interface SkTransactionRecordMapper {
    Page<SkTransactionRecord> queryForPage(SkTransactionRecord record);

    SkTransactionRecord selectByPrimaryKey(String id);

    int update(SkTransactionRecord record);

    int insert(SkTransactionRecord record);

    int deleteByPrimaryKey(String id);
}