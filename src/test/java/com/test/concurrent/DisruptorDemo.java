package com.test.concurrent;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.*;

import java.nio.*;
import java.util.concurrent.*;

/**
 * Created by bj-s2-w1631 on 18-8-13.
 */
public class DisruptorDemo {
    private static class PCData {
        private long value;

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }

    private static class Consumer implements WorkHandler<PCData> {

        @Override
        public void onEvent(PCData event) throws Exception {
            System.out.println(Thread.currentThread().getId() + ":Event: --" + event.getValue() * event.getValue() + "--");
        }
    }

    private static class PCDataFactory implements EventFactory<PCData> {

        @Override
        public PCData newInstance() {
            return new PCData();
        }
    }

    private static class Producer {
        private final RingBuffer<PCData> ringBuffer;

        public Producer(RingBuffer<PCData> ringBuffer) {
            this.ringBuffer = ringBuffer;
        }


        public void pushData(ByteBuffer bb) {
            long sequence  = ringBuffer.next();
            try {
                PCData event = ringBuffer.get(sequence);
                event.setValue(bb.getLong(0));
            } finally {
                ringBuffer.publish(sequence);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Executor executor = Executors.newCachedThreadPool();
        PCDataFactory factory = new PCDataFactory();
        int bufferSize = 1024;
        Disruptor<PCData> disruptor = new Disruptor<PCData>(factory,bufferSize, executor, ProducerType.MULTI, new BlockingWaitStrategy());
        disruptor.handleEventsWithWorkerPool(new Consumer(), new Consumer(), new Consumer(), new Consumer());
        disruptor.start();
        RingBuffer<PCData> ringBuffer = disruptor.getRingBuffer();
        Producer producer = new Producer(ringBuffer);
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long i = 0; true; i++) {
            bb.putLong(0, i);
            producer.pushData(bb);
            Thread.sleep(100);
            System.out.println("add data "+i);
        }
    }
}
