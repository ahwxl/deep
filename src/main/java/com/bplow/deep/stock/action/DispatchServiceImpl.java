package com.bplow.deep.stock.action;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.domain.ServiceResult;
import com.bplow.deep.stock.domain.SkCustomerWarn;
import com.bplow.deep.stock.service.CustomerWarnService;
import com.bplow.deep.stock.service.DispatchService;
import com.bplow.deep.stock.service.ObserverService;

@Service("dispatchService")
public class DispatchServiceImpl implements DispatchService {

    @Autowired
    private ObserverService     observerService;

    @Autowired
    private CustomerWarnService customerWarnService;

    ExecutorService             executor = (ExecutorService) Executors.newFixedThreadPool(5);

    @Override
    public void dispatchAction() {

        //获取所有用户id 和 股票id

        List<SkCustomerWarn> warns = customerWarnService.obtainAllUserStockId();

        for (SkCustomerWarn warn : warns) {
            CaculateWorker worker = new CaculateWorker(this.observerService, warn.getUserId(),
                warn.getStockId());

            Future<ServiceResult> result = executor.submit(worker);

            if (result.isDone()) {
                System.out.println("=====");
            }
        }

    }

}
