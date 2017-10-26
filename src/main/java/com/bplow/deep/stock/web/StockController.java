package com.bplow.deep.stock.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkWarehousePositon;
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
    
    @RequestMapping(value = "/stockWareHouseList")
    @ResponseBody
    public Page<SkWarehousePositon> queryList(HttpServletRequest httpRequest, Model view,SkWarehousePositon record){
    	
    	Page<SkWarehousePositon> page = stockWareHouseService.queryWarehouse(record);
    	
    	return page;
    }
    
    @RequestMapping(value = "/queryStock")
    @ResponseBody
    public SkWarehousePositon queryStock(HttpServletRequest httpRequest, Model view,SkWarehousePositon record){
        
        SkWarehousePositon skWarehousePositon = stockWareHouseService.queryWarehouseById(record);
        
        return skWarehousePositon;
    }
    
    @RequestMapping(value = "/addStock")
    @ResponseBody
    public String addStock(HttpServletRequest httpRequest, Model view,SkWarehousePositon record){
        
    	stockWareHouseService.createWarehouse(record);
        
        return "添加成功！";
    }
    
    @RequestMapping(value = "/delStock")
    @ResponseBody
    public String delStock(HttpServletRequest httpRequest, Model view,SkWarehousePositon record){
    	
    	stockWareHouseService.deleleWarehouse(record);
        
        return "刪除成功！";
    }
    
    @RequestMapping(value = "/modifyStock")
    @ResponseBody
    public String modifyStock(HttpServletRequest httpRequest, Model view,SkWarehousePositon record){
        
        stockWareHouseService.updateWarehouse(record);
        
        return "{\"responseMsg\":\"修改成功！\"}";
        
    }
    
}
