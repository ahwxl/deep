package com.bplow.deep.base.pagination;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class PageResultInterceptor implements MethodInterceptor{

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        
        Object obj = invocation.proceed();
        
        Pagination page = new Pagination<Object>();
        if(obj != null){
            
        }
        
        return page;
    }

}
