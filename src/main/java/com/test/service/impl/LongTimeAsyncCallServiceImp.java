package com.test.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.test.callback.LongTermTaskCallback;
import com.test.service.LongTimeAsyncCallService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class LongTimeAsyncCallServiceImp implements LongTimeAsyncCallService {
    private final int corePoolSize = 4;
    private final int needSeconds = 5;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(corePoolSize);

    @Override
    public void makeRemoteCallAndUnknownWhenFinish(final LongTermTaskCallback callback) {
        System.out.println("完成任务需要：" + needSeconds + "秒");
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                callback.callback("异步调用");
            }
        }, needSeconds, TimeUnit.SECONDS);
    }
}
