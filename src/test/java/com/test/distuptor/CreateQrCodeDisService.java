package com.test.distuptor;

import com.gome.o2m.ic.scancode.model.GoodsTwoCode;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by songyigui on 2016/11/2.
 */
//@Component
public class CreateQrCodeDisService {
//    private static final Logger log = LoggerFactory.getLogger(CreateQrCodeDisService.class);

    //    public void createQrCodeDis() throws Exception {
   /* public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        int buff = 1024;
        ExecutorService executor = Executors.newFixedThreadPool(4);
//        ThreadFactory threadFactory = Executors.defaultThreadFactory();
//        MyThreadFactory threadFactory = new MyThreadFactory("MyThreadFactory");
        //创建Disruptor
        Disruptor<GoodsTwoCode> disruptor = new Disruptor<GoodsTwoCode>(new EventFactory<GoodsTwoCode>() {
            @Override
            public GoodsTwoCode newInstance() {
                return new GoodsTwoCode();
            }
        }, buff, executor, ProducerType.SINGLE, new YieldingWaitStrategy());
        //创建消费者
        disruptor.handleEventsWith(new EventHandler<GoodsTwoCode>() {
            @Override
            public void onEvent(GoodsTwoCode event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println(event.getLockThreadId());
            }
        }).then(new EventHandler<GoodsTwoCode>() {
            @Override
            public void onEvent(GoodsTwoCode event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println(event.getChannelCode());
            }
        });

        disruptor.start();
        CountDownLatch latch = new CountDownLatch(1);
        executor.submit(new CreateQrCodeProducer(disruptor, latch));
        latch.await();
        disruptor.shutdown();
        executor.shutdown();

        log.info("共耗时：" + (System.currentTimeMillis() - start));
    }*/

    private static final int BUFF_SIZE = 1024 * 1024;
//    private static final long ITERATIONS     = 1000L * 1000L * 100L;

    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    //    private final CyclicBarrier   barrier  = new CyclicBarrier(NUM_PUBLISHERS + 1);
    private final CountDownLatch  latch    = new CountDownLatch(1);

    public void executeDisruptor() throws InterruptedException {
        long start = System.currentTimeMillis();

        //创建Disruptor
        Disruptor<GoodsTwoCode> disruptor = new Disruptor<GoodsTwoCode>(new EventFactory<GoodsTwoCode>() {
            @Override
            public GoodsTwoCode newInstance() {
                return new GoodsTwoCode();
            }
        }, BUFF_SIZE, Executors.defaultThreadFactory(), ProducerType.SINGLE, new BlockingWaitStrategy());
        //创建消费者
        disruptor.handleEventsWithWorkerPool(new ValueWorkHandler()).
                then(new EventHandler<GoodsTwoCode>() {
                    @Override
                    public void onEvent(GoodsTwoCode event, long sequence, boolean endOfBatch) throws Exception {
                        System.out.println("end:" + event.getLockThreadId());
                        System.out.println("end-seq:" + sequence);
                    }
                });
//        disruptor.handleEventsWith(new ValueEventHandler()).
//                then(new EventHandler<GoodsTwoCode>() {
//                    @Override
//                    public void onEvent(GoodsTwoCode event, long sequence, boolean endOfBatch) throws Exception {
//                        System.out.println("end:" + event.getLockThreadId());
//                        System.out.println("end-seq:" + sequence);
//                    }
//                });

        RingBuffer<GoodsTwoCode> buffer = disruptor.start();
        executor.execute(new ValuePublisher(buffer, latch));
//        for (int i = 0; i < 100; i++) {
//            long sequence = buffer.next();
//            buffer.get(sequence).setLockThreadId(i);
//            buffer.publish(sequence);
//            System.out.println("start:" + buffer.get(sequence).getLockThreadId());
//            System.out.println("start-seq:"+sequence);
//            Thread.sleep(1000);
//        }

        latch.await();
        disruptor.shutdown();
        executor.shutdown();

        System.out.println("共耗时：" + (System.currentTimeMillis() - start));

    }

    public static void main(String[] args) throws InterruptedException {
        CreateQrCodeDisService service = new CreateQrCodeDisService();
        service.executeDisruptor();
    }

}
