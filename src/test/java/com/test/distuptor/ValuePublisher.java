package com.test.distuptor;

import com.lmax.disruptor.RingBuffer;
import com.test.entity.GoodsTwoCode;

import java.util.concurrent.CountDownLatch;

/**
 * 生产者
 * Created by songyigui on 2016/11/2.
 */
public class ValuePublisher implements Runnable {
    private final RingBuffer<GoodsTwoCode> buffer;
    private final CountDownLatch           latch;

    public ValuePublisher(RingBuffer<GoodsTwoCode> buffer, CountDownLatch latch) {
        this.buffer = buffer;
        this.latch = latch;
    }

    //    private static final EventTranslatorOneArg TRANSLATOR = new EventTranslatorOneArg<GoodsTwoCode, Long>() {
//        @Override
//        public void translateTo(GoodsTwoCode event, long sequence, Long value) {
//            event.setLockThreadId(value);
//        }
//    };
//
//
//    public void onData(final Long value) {
//        System.out.println("produce:" + value);
//        ringBuffer.publishEvent(TRANSLATOR, value);
//    }


    @Override
    public void run() {
        try {
            for (int i = 0; i < 1000000; i++) {
                long sequence = buffer.next();
                buffer.get(sequence).setLockThreadId(i);
                buffer.publish(sequence);
                System.out.println("start:" + buffer.get(sequence).getLockThreadId());
                System.out.println("start-seq:" + sequence);
//                Thread.sleep(1000);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            latch.countDown();
        }

    }
}
