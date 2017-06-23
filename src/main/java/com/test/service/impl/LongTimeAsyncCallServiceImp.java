package com.test.service.impl;

import com.test.callback.LongTermTaskCallback;
import com.test.service.LongTimeAsyncCallService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service("longTimeAsyncCallService")
public class LongTimeAsyncCallServiceImp implements LongTimeAsyncCallService {
    private final int corePoolSize = 4;
    private final int needSeconds = 5;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(corePoolSize);

    @Override
    @Async
    public void makeRemoteCallAndUnknownWhenFinish(final LongTermTaskCallback callback) {
        System.out.println("完成任务需要：" + needSeconds + "秒");
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                callback.callback("异步调用");
            }
        }, needSeconds, TimeUnit.SECONDS);
    }

    @Override
    @Async
    public void asyncDoSth() {
        try {
            System.out.println("async do sth start");
            Thread.sleep(5000);
            System.out.println("async do sth stop");
        } catch (InterruptedException e) {
        }
    }
}
