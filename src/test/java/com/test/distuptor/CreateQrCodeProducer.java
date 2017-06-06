package com.test.distuptor;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import com.test.entity.GoodsTwoCode;

import java.util.concurrent.CountDownLatch;

/**
 * Producer
 * Created by songyigui on 2016/11/2.
 */
public class CreateQrCodeProducer implements Runnable {
    Disruptor<GoodsTwoCode> disruptor;
    private CountDownLatch latch;
    private static int LOOP = 10;

    public CreateQrCodeProducer(Disruptor<GoodsTwoCode> disruptor, CountDownLatch latch) {
        this.disruptor = disruptor;
        this.latch = latch;
    }

    @Override
    public void run() {
        CreateQrCodeEventTranslator translator = new CreateQrCodeEventTranslator();
        for (int i = 0; i < LOOP; i++) {
            disruptor.publishEvent(translator);
            System.out.println("Producer create:"+i);
            try {
                Thread.currentThread().sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        latch.countDown();

    }
}

class CreateQrCodeEventTranslator implements EventTranslator<GoodsTwoCode> {

    @Override
    public void translateTo(GoodsTwoCode event, long sequence) {
        event.setLockThreadId((long) (Math.random()*100));
    }
}
