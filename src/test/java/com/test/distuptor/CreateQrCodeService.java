package com.test.distuptor;

import com.gome.o2m.Response;
import com.gome.o2m.ic.scancode.model.GoodsTwoCode;
import com.gome.o2m.ic.scancode.service.TwoCodeReadService;
import com.gome.o2m.ic.scancode.service.TwoCodeWriteService;
import com.gome.o2m.util.Constants;
import com.gome.o2m.util.DateUtils;
import com.gome.o2m.util.ImagesPathUtil;
import com.google.zxing.EncodeHintType;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component("createQrCodeService")
public class CreateQrCodeService {
    private static final Logger log       = LoggerFactory.getLogger(CreateQrCodeService.class);
    private static final int    threadNum = 4;

    private final CountDownLatch  latch    = new CountDownLatch(threadNum);
    private final ExecutorService executor = Executors.newFixedThreadPool(threadNum);

    @Autowired
    private TwoCodeReadService  twoCodeReadService;
    @Autowired
    private TwoCodeWriteService twoCodeWriteService;


    public void createQrCode() {
        log.info("JOB开始====================");
        long start = System.currentTimeMillis();
//        final CountDownLatch signal = new CountDownLatch(threadNum);
//        final int cpuCore = Runtime.getRuntime().availableProcessors();
//        final int threadNum = cpuCore - 1;
//        final int threadNum = 2 * cpuCore;
//        final int threadNum = (int) (cpuCore / (1 - 0.9));

        final ConcurrentLinkedQueue<GoodsTwoCode> queue = new ConcurrentLinkedQueue<GoodsTwoCode>();
        List<GoodsTwoCode> resultList = new ArrayList<GoodsTwoCode>();
        try {
            Response<List<GoodsTwoCode>> response = twoCodeReadService.selectUnCreatedCode();

            if (response.isOk()) {
                log.info("查询结果：" + response.getResult().size());
                queue.addAll(response.getResult());
            }

            if (!queue.isEmpty()) {
                List<CreatPicThread> list = new ArrayList<CreatPicThread>();
                for (int i = 0; i < 50; i++) {
                    CreatPicThread thread = new CreatPicThread(queue, latch);
                    list.add(thread);
//                    Future<GoodsTwoCode> future = executor.submit(new CreatPicThread(queue));
//                    GoodsTwoCode result = future.get();
//                    log.info("任务返回结果：" + new ObjectMapper().writeValueAsString(result));
//                    log.info("是否完成：" + future.isDone());
//                    resultList.add(result);
                }
                List<Future<GoodsTwoCode>> futures = executor.invokeAll(list);
                for (int i = 0; i < futures.size(); i++) {
                    Future<GoodsTwoCode> future = futures.get(i);
                    GoodsTwoCode result = future.get();
                    log.info("是否完成：" + future.isDone());
                    log.info("任务返回结果：" + result.getUrl());
                    if (result != null) {
                        resultList.add(result);
                    }
                }
                latch.await();
                executor.shutdown();


            } else {
                executor.shutdown();
            }

            for (GoodsTwoCode goodsTwoCode : resultList) {
                twoCodeWriteService.updateTwoCode(goodsTwoCode);
            }


//            signal.await();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("异常：", e);
        }
//        finally {
//            executor.shutdown();
//        }

//        while (!executor.isTerminated()) {
//
//        }


//        if (goods.getResult() != null && goods.getResult().size() > 0) {
//
//            List<List<GoodsTwoCode>> splitList = ListSplitUtil.splitList(goods.getResult(), threadNum);
//
//
//            for (int i = 0; i < threadNum; i++) { // 开threadNum个线程
//                if (splitList.get(i) != null) {
//                    Runnable task = new CreatPicThread(splitList.get(i));
//                    executor.execute(task);
//                }
//            }

//            try {
//                threadsSignal.await();
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }
//        executor.shutdown();

        log.info("时间：" + (System.currentTimeMillis() - start));
        log.info("JOB结束====================");

    }

    public static GoodsTwoCode createQrImage(GoodsTwoCode goodsTwoCode) throws IOException {
        String twoCodeMess = ImagesPathUtil.getEwmUrl() + "?storeCode=" + goodsTwoCode.getStoreCode()
                + "&skuCode=" + goodsTwoCode.getSkuId();
        QRCode code = QRCode.from(twoCodeMess).to(ImageType.JPG)
                .withHint(EncodeHintType.MARGIN, 0)
                .withSize(ImagesPathUtil.getSize(), ImagesPathUtil.getSize());
//        ByteArrayOutputStream out = code.stream();
        ImagesPathUtil.makdir(Constants.TWO_CODE_DIR + File.separator + goodsTwoCode.getSkuId() + "_"
                + goodsTwoCode.getStoreCode());
        String url = Constants.TWO_CODE_DIR + "/" + goodsTwoCode.getSkuId() + "_" + goodsTwoCode.getStoreCode()
                + "/" + goodsTwoCode.getSkuId() + "_" + goodsTwoCode.getStoreCode() + ".jpg";
        String pic = ImagesPathUtil.getBasePath() + url;
        OutputStream out = new FileOutputStream(new File(pic));
//        out.write(out.toByteArray());
        code.writeTo(out);
        out.close();

        goodsTwoCode.setUrl(url);
        goodsTwoCode.setWidth(ImagesPathUtil.getSize());
        goodsTwoCode.setHeight(ImagesPathUtil.getSize());
        goodsTwoCode.setTwoCodeMess(twoCodeMess);
        goodsTwoCode.setStatus(1);
        goodsTwoCode.setLockThreadId(Thread.currentThread().getId());

        return goodsTwoCode;
    }


    private static class CreatPicThread implements Callable<GoodsTwoCode> {
        private static final Logger log = LoggerFactory.getLogger(CreatPicThread.class);

        //    private CountDownLatch signal;
        private final ConcurrentLinkedQueue<GoodsTwoCode> queue;
        private final CountDownLatch                      latch;

        public CreatPicThread(ConcurrentLinkedQueue<GoodsTwoCode> queue, CountDownLatch latch) {
//        this.signal = signal;
            this.queue = queue;
            this.latch = latch;
        }


        @Override
        public GoodsTwoCode call() throws Exception {
            try {
                long start = System.currentTimeMillis();
                log.info("开始时间：" + DateUtils.formatDateTime(new Date()));
                GoodsTwoCode goodsTwoCode = queue.poll();
//            latch.countDown();

                if (goodsTwoCode == null) {
//            throw new NullPointerException("code is null");
                    return null;
                } else {
                    goodsTwoCode = CreateQrCodeService.createQrImage(goodsTwoCode);
                    log.info("线程数：" + latch.getCount());
                    log.info("构造结果：" + goodsTwoCode.getUrl());
                    log.info("线程" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
                    log.info("结束时间：" + DateUtils.formatDateTime(new Date()));
                    log.info("间隔：" + (System.currentTimeMillis() - start));
//            signal.countDown();
                    log.info("剩余数量：" + queue.size());
//                if ("A004".equals(goodsTwoCode.getStoreCode())) {
//                    int i = 1 / 0;
//                }
                    return goodsTwoCode;
                }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Thread.currentThread().interrupt();
//            return null;
            } finally {
                if (latch != null) {
                    latch.countDown();
                }
            }

        }


    }
}

//    private GoodsTwoCode createQrImage(GoodsTwoCode goodsTwoCode) throws Exception {
//        String twoCodeMess = ImagesPathUtil.getEwmUrl() + "?storeCode=" + goodsTwoCode.getStoreCode() + "&skuCode=" + goodsTwoCode.getSkuId();
//        QRCode code = QRCode.from(twoCodeMess).to(ImageType.JPG).withHint(EncodeHintType.MARGIN, 0).withSize(ImagesPathUtil.getSize(), ImagesPathUtil.getSize());
//        ByteArrayOutputStream out = code.stream();
//        ImagesPathUtil.makdir(Constants.TWO_CODE_DIR + File.separator + goodsTwoCode.getSkuId() + "_" + goodsTwoCode.getStoreCode());
//        String url = Constants.TWO_CODE_DIR + "/" + goodsTwoCode.getSkuId() + "_" + goodsTwoCode.getStoreCode() + "/" + goodsTwoCode.getSkuId() + "_" + goodsTwoCode.getStoreCode() + ".jpg";
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

