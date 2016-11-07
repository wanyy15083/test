package com.test.distuptor;

import com.gome.o2m.ic.scancode.model.GoodsTwoCode;
import com.lmax.disruptor.WorkHandler;

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
