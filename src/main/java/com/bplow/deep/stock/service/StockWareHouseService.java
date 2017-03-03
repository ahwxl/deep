package com.bplow.deep.stock.service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkWarehousePositon;

/**
 * 仓库、持仓、统计
 * 
 * @author wangxiaolei
 * @version $Id: StockWareHouseService.java, v 0.1 2017年3月3日 下午12:45:28 wangxiaolei Exp $
 */

public interface StockWareHouseService {
    
    Page<SkWarehousePositon> queryWarehouse(SkWarehousePositon position);
    
    SkWarehousePositon createWarehouse(SkWarehousePositon position);
    
    void deleleWarehouse(SkWarehousePositon position);
    

}
