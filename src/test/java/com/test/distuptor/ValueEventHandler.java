package com.test.distuptor;

import com.gome.o2m.ic.scancode.model.GoodsTwoCode;
import com.lmax.disruptor.EventHandler;

/**
 * 消费者
 * Created by songyigui on 2016/11/3.
 */
public class ValueEventHandler implements EventHandler<GoodsTwoCode> {
    @Override
    public void onEvent(GoodsTwoCode event, long sequence, boolean endOfBatch) throws Exception {
        event.setLockThreadId(event.getLockThreadId() * 2);
        System.out.println("handle:"+event.getLockThreadId());
        System.out.println("handle-seq:"+sequence);
    }
}
