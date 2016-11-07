package com.test.distuptor;

import com.gome.o2m.Response;
import com.gome.o2m.ic.scancode.model.GoodsTwoCode;
import com.gome.o2m.ic.scancode.service.TwoCodeReadService;
import com.gome.o2m.ic.scancode.service.TwoCodeWriteService;
import com.gome.o2m.util.Constants;
import com.gome.o2m.util.ImagesPathUtil;
import com.google.zxing.EncodeHintType;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;


@Component("testCreateQrCodeService")
public class TestCreateQrCodeService {
    private static final Logger log = LoggerFactory.getLogger(TestCreateQrCodeService.class);

    private static final int BUFF_SIZE = 1024 * 2;

    private final CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private TwoCodeReadService  twoCodeReadService;
    @Autowired
    private TwoCodeWriteService twoCodeWriteService;

    public void testCreateQrCode() {
        long start = System.currentTimeMillis();

        try {

            //创建Disruptor
            Disruptor<GoodsTwoCode> disruptor = new Disruptor<GoodsTwoCode>(new EventFactory<GoodsTwoCode>() {
                @Override
                public GoodsTwoCode newInstance() {
                    return new GoodsTwoCode();
                }
            }, BUFF_SIZE, Executors.defaultThreadFactory(), ProducerType.SINGLE, new BlockingWaitStrategy());
            //消费者
            disruptor.handleEventsWithWorkerPool(new QrCodeWorkHandler(), new QrCodeWorkHandler(), new QrCodeWorkHandler(), new QrCodeWorkHandler()).
                    then(new EventHandler<GoodsTwoCode>() {
                        @Override
                        public void onEvent(GoodsTwoCode event, long sequence, boolean endOfBatch) throws Exception {
                            System.out.println(event.getUrl() + "-----" + event.getLockThreadId());
                            twoCodeWriteService.updateTwoCode(event);
                        }
                    });
//            disruptor.handleEventsWith(new EventHandler<GoodsTwoCode>() {
//                @Override
//                public void onEvent(GoodsTwoCode event, long sequence, boolean endOfBatch) throws Exception {
//                    createQrImage(event);
//                    System.out.println("comsumer1:" + event.getUrl());
//                }
//            }).then(new EventHandler<GoodsTwoCode>() {
//                @Override
//                public void onEvent(GoodsTwoCode event, long sequence, boolean endOfBatch) throws Exception {
//                    twoCodeWriteService.updateTwoCode(event);
//                }
//            });

            RingBuffer<GoodsTwoCode> buffer = disruptor.start();
            List<GoodsTwoCode> list = new ArrayList<GoodsTwoCode>();
            Response<List<GoodsTwoCode>> response = twoCodeReadService.selectUnCreatedCode();
            if (response.isOk()) {
                System.out.println("查询结果：" + response.getResult().size());
                list = response.getResult();
            }

            Thread t = new Thread(new Publisher(buffer, latch, list));
            t.start();
            latch.await();

            //生产者
//        for (final GoodsTwoCode twoCode : list) {
//            buffer.publishEvent(new EventTranslator<GoodsTwoCode>() {
//                @Override
//                public void translateTo(GoodsTwoCode event, long sequence) {
//                    event = twoCode;
//                    log.info("produce:" + event.getStoreCode() + "-" + event.getSkuId());
//                }
//            });
//        }

//            disruptor.shutdown();
//            t.interrupt();


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("共耗时：" + (System.currentTimeMillis() - start));
    }


//        try {
//            long start1 = System.currentTimeMillis();
//            final BlockingQueue<GoodsTwoCode> queue = new LinkedBlockingQueue<GoodsTwoCode>();
//            Response<List<GoodsTwoCode>> response = twoCodeReadService.selectUnCreatedCode();
//            List<GoodsTwoCode> list = new ArrayList<GoodsTwoCode>();
//            if (response.isOk()) {
//                log.info("查询结果：" + new ObjectMapper().writeValueAsString(response.getResult()));
//                list = response.getResult();
//            }
//            final int cpuCore = Runtime.getRuntime().availableProcessors();
//            final int threadNum = cpuCore + 1;
////            final int threadNum = 2 * cpuCore;
////            final int threadNum = (int) (cpuCore / (1 - 0.9));
//            ExecutorService executor = Executors.newFixedThreadPool(threadNum);
//            if (list != null && list.size() > 0) {
//                for (final GoodsTwoCode goodsTwoCode : list) {
//                    executor.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            while (true) {
//                                try {
//                                    long start = System.currentTimeMillis();
//                                    GoodsTwoCode result = createQrImage(goodsTwoCode);
//                                    queue.put(result);
//                                    log.info("Producer[" + Thread.currentThread().getName() + "]put" + goodsTwoCode.getUrl());
//                                    log.info("Producer间隔：" + (System.currentTimeMillis() - start));
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//
//                    });
//
//
//                }
//                executor.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        while (true) {
//                            try {
//                                long start = System.currentTimeMillis();
//                                GoodsTwoCode result = queue.take();
//                                log.info("Comsumer[" + Thread.currentThread().getName() + "]take" + result.getUrl());
//                                twoCodeWriteService.updateTwoCode(result);
//                                log.info("Comsumer间隔：" + (System.currentTimeMillis() - start));
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//                Thread.sleep(3000);
//
//
//            } else {
//                executor.shutdown();
//            }
//            executor.shutdown();
//            log.info("All间隔：" + (System.currentTimeMillis() - start1));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}

class QrCodeWorkHandler implements WorkHandler<GoodsTwoCode> {

    @Override
    public void onEvent(GoodsTwoCode event) throws Exception {
        createQrImage(event);
        System.out.println("comsumer1:" + event.getUrl());
    }

    private GoodsTwoCode createQrImage(GoodsTwoCode goodsTwoCode) throws IOException {
        String twoCodeMess = ImagesPathUtil.getEwmUrl() + "?storeCode=" + goodsTwoCode.getStoreCode()
                + "&skuCode=" + goodsTwoCode.getSkuId();
        QRCode code = QRCode.from(twoCodeMess).to(ImageType.JPG)
                .withHint(EncodeHintType.MARGIN, 0)
                .withSize(ImagesPathUtil.getSize(), ImagesPathUtil.getSize());
        ByteArrayOutputStream out = code.stream();
        ImagesPathUtil.makdir(Constants.TWO_CODE_DIR + File.separator + goodsTwoCode.getSkuId() + "_"
                + goodsTwoCode.getStoreCode());
        String url = Constants.TWO_CODE_DIR + "/" + goodsTwoCode.getSkuId() + "_" + goodsTwoCode.getStoreCode()
                + "/" + goodsTwoCode.getSkuId() + "_" + goodsTwoCode.getStoreCode() + ".jpg";
        String pic = ImagesPathUtil.getBasePath() + url;
        FileOutputStream fout = new FileOutputStream(new File(pic));
        fout.write(out.toByteArray());
        fout.flush();
        fout.close();
        out.close();

        goodsTwoCode.setUrl(url);
        goodsTwoCode.setWidth(ImagesPathUtil.getSize());
        goodsTwoCode.setHeight(ImagesPathUtil.getSize());
        goodsTwoCode.setTwoCodeMess(twoCodeMess);
        goodsTwoCode.setStatus(1);
        goodsTwoCode.setLockThreadId(Thread.currentThread().getId());

        return goodsTwoCode;
    }
}

class Publisher implements Runnable {
    private final RingBuffer<GoodsTwoCode> buffer;
    private final CountDownLatch           latch;
    private final List<GoodsTwoCode>       list;

    public Publisher(RingBuffer<GoodsTwoCode> buffer, CountDownLatch latch, List<GoodsTwoCode> list) {
        this.buffer = buffer;
        this.latch = latch;
        this.list = list;
    }

    private final EventTranslatorOneArg TRANSLATOR = new EventTranslatorOneArg<GoodsTwoCode, GoodsTwoCode>() {
        @Override
        public void translateTo(GoodsTwoCode event, long sequence, GoodsTwoCode value) {
            try {
                BeanUtils.copyProperties(event, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void run() {
        try {
            for (final GoodsTwoCode twoCode : list) {
                buffer.publishEvent(TRANSLATOR, twoCode);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            latch.countDown();
        }
    }
}


//
//class Producer implements Runnable {
//    private BlockingQueue<GoodsTwoCode> buffer;
//    private GoodsTwoCode                goodsTwoCode;
//
//    public Producer(BlockingQueue<GoodsTwoCode> buffer, GoodsTwoCode goodsTwoCode) {
//        this.buffer = buffer;
//        this.goodsTwoCode = goodsTwoCode;
//    }
//
//    @Override
//    public void run() {
//        while (true) {
//            try {
//                GoodsTwoCode result = createQrImage(goodsTwoCode);
//                buffer.put(result);
//                log.info("Producer[" + Thread.currentThread().getName() + "]put" + goodsTwoCode.getUrl());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    private GoodsTwoCode createQrImage(GoodsTwoCode goodsTwoCode) throws IOException {
//        String twoCodeMess = ImagesPathUtil.getEwmUrl() + "?storeCode=" + goodsTwoCode.getStoreCode()
//                + "&skuCode=" + goodsTwoCode.getSkuId();
//        QRCode code = QRCode.from(twoCodeMess).to(ImageType.JPG)
//                .withHint(EncodeHintType.MARGIN, 0)
//                .withSize(ImagesPathUtil.getSize(), ImagesPathUtil.getSize());
//        ByteArrayOutputStream out = code.stream();
//        ImagesPathUtil.makdir(Constants.TWO_CODE_DIR + File.separator + goodsTwoCode.getSkuId() + "_"
//                + goodsTwoCode.getStoreCode());
//        String url = Constants.TWO_CODE_DIR + "/" + goodsTwoCode.getSkuId() + "_" + goodsTwoCode.getStoreCode()
//                + "/" + goodsTwoCode.getSkuId() + "_" + goodsTwoCode.getStoreCode() + ".jpg";
//        String pic = ImagesPathUtil.getBasePath() + url;
//        FileOutputStream fout = new FileOutputStream(new File(pic));
//        fout.write(out.toByteArray());
//        fout.flush();
//        fout.close();
//        out.close();
//
//        goodsTwoCode.setUrl(url);
//        goodsTwoCode.setWidth(ImagesPathUtil.getSize());
//        goodsTwoCode.setHeight(ImagesPathUtil.getSize());
//        goodsTwoCode.setTwoCodeMess(twoCodeMess);
//        goodsTwoCode.setStatus(1);
//        goodsTwoCode.setLockThreadId(Thread.currentThread().getId());
//
//        return goodsTwoCode;
//    }
//}
//
//class Consumer implements Runnable {
//
//    private BlockingQueue<GoodsTwoCode> buffer;
//
//    public Consumer(BlockingQueue<GoodsTwoCode> buffer) {
//        this.buffer = buffer;
//    }
//
//
//    @Override
//    public void run() {
//        while (true) {
//            try {
//                GoodsTwoCode goodsTwoCode = buffer.take();
//
//                System.out.println("Consumer[" + Thread.currentThread().getName() + "]get" + goodsTwoCode.getUrl());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
