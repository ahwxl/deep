package com.bplow.deep.stock.service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkTransactionRecord;

public interface TransactionRecordService {
    
    public SkTransactionRecord createTransactionRecord(SkTransactionRecord record);

    public void deleteTransactionRecord(SkTransactionRecord record);
    
    public void updateTransactionRecord(SkTransactionRecord record);
    
    public Page<SkTransactionRecord> queryForPage(SkTransactionRecord record);
    
    public SkTransactionRecord querySkTransactionRecord(SkTransactionRecord record);
    
}
