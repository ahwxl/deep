package com.bplow.deep.stock.service;

import com.bplow.deep.base.pagination.Page;
import com.bplow.deep.stock.domain.SkCustomerWarn;

/**
 * 客户定义预警管理
 * 
 * @author wangxiaolei
 * @version $Id: CustomerWarnService.java, v 0.1 2017年3月3日 下午3:50:28 wangxiaolei Exp $
 */
public interface CustomerWarnService {
    
    Page<SkCustomerWarn> queryCustomerWarns(SkCustomerWarn warn);
    
    SkCustomerWarn queryCustomerWarn(SkCustomerWarn warn);
    
    int createCustomerWarn(SkCustomerWarn warn);
    
    int deleteCustomerWarn(SkCustomerWarn warn);
    
    int updateCustomerWarn(SkCustomerWarn warn);

}
