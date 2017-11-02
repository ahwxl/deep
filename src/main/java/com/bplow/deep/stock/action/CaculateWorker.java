package com.bplow.deep.stock.action;

import java.util.concurrent.Callable;

import com.bplow.deep.base.domain.ServiceResult;
import com.bplow.deep.stock.service.ObserverService;

/**
 * 一个任务只完成 某个用户的指定stockId的任务
 * 
 * @author wangxiaolei
 * @version $Id: CaculateWorker.java, v 0.1 2017年11月2日 下午5:21:32 wangxiaolei Exp $
 */
public class CaculateWorker implements Callable<ServiceResult> {

    private ObserverService observerService;

    private String          userId;

    private String          stockId;

    public CaculateWorker(ObserverService observerService, String userId, String stockId) {
        this.observerService = observerService;
        this.userId = userId;
        this.stockId = stockId;
    }

    @Override
    public ServiceResult call() throws Exception {
        ServiceResult result = new ServiceResult();

        observerService.observer(userId, stockId);

        return result;
    }

}
