package com.test.distuptor;

import com.lmax.disruptor.WorkHandler;
import com.test.entity.GoodsTwoCode;

/**
 * Created by songyigui on 2016/11/4.
 */
public class ValueWorkHandler implements WorkHandler<GoodsTwoCode> {
    @Override
    public void onEvent(GoodsTwoCode event) throws Exception {
//        event.setLockThreadId(event.getLockThreadId() * 2);
        System.out.println("handle:" + event.getLockThreadId());
    }
}
