package com.bplow.deep.stock.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bplow.deep.stock.service.StockWareHouseService;


@Controller
@RequestMapping("stock")
public class StockController {
    
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private StockWareHouseService stockWareHouseService;

    @RequestMapping(value = "/stockWareHouse")
    public String index(HttpServletRequest httpRequest, Model view) {

        return "stock/stockWarehouse";
    }
    
    @RequestMapping(value = "/addStock")
    @ResponseBody
    public String addStock(){
        
        
        return "";
    }
    
    @RequestMapping(value = "/delStock")
    @ResponseBody
    public String delStock(){
        
        return "";
    }
    
}
