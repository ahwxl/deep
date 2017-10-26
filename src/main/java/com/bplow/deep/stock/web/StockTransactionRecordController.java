package com.bplow.deep.stock.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkTransactionRecord;
import com.bplow.deep.stock.service.TransactionRecordService;

@Controller
@RequestMapping("stock")
public class StockTransactionRecordController {

    @Autowired
    private TransactionRecordService transactionRecordService;

    @RequestMapping(value = "/translist")
    public String index(HttpServletRequest httpRequest, Model view) {

        return "stock/transactions";
    }

    @RequestMapping(value = "/queryTransList")
    @ResponseBody
    public Page<SkTransactionRecord> queryList(HttpServletRequest httpRequest, Model view,
                                               SkTransactionRecord record) {

        Page<SkTransactionRecord> page = transactionRecordService.queryForPage(record);

        return page;
    }
    
    @RequestMapping(value = "/queryTransaction")
    @ResponseBody
    public SkTransactionRecord queryTransaction(HttpServletRequest httpRequest, Model view,
                                               SkTransactionRecord record) {

        SkTransactionRecord transaction = transactionRecordService.querySkTransactionRecord(record);

        return transaction;
    }

    @RequestMapping(value = "/createTrans")
    @ResponseBody
    public String createTransactionRecord(HttpServletRequest httpRequest, Model view,
                                          SkTransactionRecord record) {

        transactionRecordService.createTransactionRecord(record);

        return "添加成功";
    }

    @RequestMapping(value = "/delTrans")
    @ResponseBody
    public String delTransRecord(HttpServletRequest httpRequest, Model view,
                                 SkTransactionRecord record) {

        transactionRecordService.deleteTransactionRecord(record);

        return "删除成功";
    }
    
    @RequestMapping(value = "/modifyTrans")
    @ResponseBody
    public String modifyTransRecord(HttpServletRequest httpRequest, Model view,
                                 SkTransactionRecord record) {

        transactionRecordService.updateTransactionRecord(record);

        return "{\"responseMsg\":\"修改成功\"}";
    }

}
