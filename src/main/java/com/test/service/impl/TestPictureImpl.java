package com.test.service.impl;

import com.google.zxing.EncodeHintType;
import com.test.entity.CallHttpRequest;
import com.test.entity.Picture;
import com.test.service.TestPicture;
import com.test.utils.DateUtils;
import com.test.utils.ImagesPathUtil;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("testPicture")
public class TestPictureImpl implements TestPicture {

	private final static Logger log = LogManager.getLogger(TestExcelServiceImpl.class);

	private static final int threadNum = 3;

	private String name = UUID.randomUUID().toString();

	@Override
	public void createPic() {
		CountDownLatch threadSignal = new CountDownLatch(threadNum);
		ExecutorService executor = Executors.newFixedThreadPool(threadNum);
		for (int i = 0; i < threadNum; i++) { // 开threadNum个线程
			Runnable task = new CreatPicThread(threadSignal);
			executor.execute(task);
		}
		try {
			threadSignal.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		executor.shutdown();
		System.out.println(Thread.currentThread().getName() + "+++++++结束.");

	}

	private class CreatPicThread implements Runnable {
		private CountDownLatch threadsSignal;

		public CreatPicThread(CountDownLatch threadsSignal) {
			this.threadsSignal = threadsSignal;
		}

		public void run() {
			System.out.println(Thread.currentThread().getName() + "开始..." + name);
			System.out.println("开始了线程：：：：" + threadsSignal.getCount());

			// do shomething

			// 核心处理逻辑

			long manyStart = System.currentTimeMillis();

			log.info("manyStart:" + DateUtils.formatDateTime(new Date()));

			for (int i = 0; i < 5; i++) {
				long oneStart = System.currentTimeMillis();
				log.info("ThreadID:" + Thread.currentThread().getId() + "ThreadName:" + Thread.currentThread().getName());

				log.info(i + "oneStart:" + DateUtils.formatDateTime(new Date()));

				String twoCodeMess = "http://www.laigome.com/store/item?storeCode=A46W" + i + "&skuId=100001487";
				QRCode code = QRCode.from(twoCodeMess).to(ImageType.JPG).withHint(EncodeHintType.MARGIN, 0).withSize(10230, 10230);
				ByteArrayOutputStream out = code.stream();

				try {
					String path = "test/";
					ImagesPathUtil.setBasePath("D:/");
					ImagesPathUtil.makdir(path);
					String name1 = "pic_" + UUID.randomUUID().toString() + ".jpg";
					log.info("filename:"+name1);
					String pic = ImagesPathUtil.getBasePath() + path + name1;
					FileOutputStream fout = new FileOutputStream(new File(pic));
					fout.write(out.toByteArray());
					fout.flush();
					fout.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				long oneEnd = System.currentTimeMillis();

				log.info(i + "oneEnd:" + DateUtils.formatDateTime(new Date()));
				log.info(i + "one:" + (oneStart - oneEnd));

			}
			long manyEnd = System.currentTimeMillis();

			log.info("manyEnd:" + DateUtils.formatDateTime(new Date()));
			log.info("many:" + (manyStart - manyEnd));

			// 用到成员变量name作为参数

			// 线程结束时计数器减1
			threadsSignal.countDown();// 必须等核心处理逻辑处理完成后才可以减1
			System.out.println(Thread.currentThread().getName() + "结束. 还有" + threadsSignal.getCount() + " 个线程");
		}
	}

	@Override
	public void createPic1() {

		log.info("ThreadID:" + Thread.currentThread().getId() + "ThreadName:" + Thread.currentThread().getName());

		long manyStart = System.currentTimeMillis();

		log.info("manyStart1:" + DateUtils.formatDateTime(new Date()));

		for (int i = 0; i < 5; i++) {
			long oneStart = System.currentTimeMillis();

			log.info("oneStart1:" + DateUtils.formatDateTime(new Date()));

			String twoCodeMess = "http://www.laigome.com/store/item?storeCode=A46W" + i + "&skuId=100001487";
			QRCode code = QRCode.from(twoCodeMess).to(ImageType.JPG).withHint(EncodeHintType.MARGIN, 0).withSize(10230, 10230);
			ByteArrayOutputStream out = code.stream();

			try {
				String path = "test/";
				ImagesPathUtil.setBasePath("D:/");
				ImagesPathUtil.makdir(path);
				String name = "pic_" + i + ".jpg";
				String pic = ImagesPathUtil.getBasePath() + path + name;
				FileOutputStream fout = new FileOutputStream(new File(pic));
				fout.write(out.toByteArray());
				fout.flush();
				fout.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			long oneEnd = System.currentTimeMillis();

			log.info("oneEnd1:" + DateUtils.formatDateTime(new Date()));
			log.info("one1:" + (oneStart - oneEnd));

		}
		long manyEnd = System.currentTimeMillis();

		log.info("manyEnd1:" + DateUtils.formatDateTime(new Date()));
		log.info("many1:" + (manyStart - manyEnd));
	}

	@Override
	public void createPic2() {

		log.info("ThreadID:" + Thread.currentThread().getId() + "ThreadName:" + Thread.currentThread().getName());

		long manyStart = System.currentTimeMillis();

		log.info("manyStart2:" + DateUtils.formatDateTime(new Date()));

		for (int i = 0; i < 5; i++) {
			long oneStart = System.currentTimeMillis();

			log.info("oneStart2:" + DateUtils.formatDateTime(new Date()));

			String twoCodeMess = "http://www.laigome.com/store/item?storeCode=A46W" + i + "&skuId=100001487";
			QRCode code = QRCode.from(twoCodeMess).to(ImageType.JPG).withHint(EncodeHintType.MARGIN, 0).withSize(10230, 10230);
			ByteArrayOutputStream out = code.stream();

			try {
				String path = "test/";
				ImagesPathUtil.setBasePath("D:/");
				ImagesPathUtil.makdir(path);
				String name = "pic_" + i + ".jpg";
				String pic = ImagesPathUtil.getBasePath() + path + name;
				FileOutputStream fout = new FileOutputStream(new File(pic));
				fout.write(out.toByteArray());
				fout.flush();
				fout.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			long oneEnd = System.currentTimeMillis();

			log.info("oneEnd2:" + DateUtils.formatDateTime(new Date()));
			log.info("one2:" + (oneStart - oneEnd));

		}
		long manyEnd = System.currentTimeMillis();

		log.info("manyEnd2:" + DateUtils.formatDateTime(new Date()));
		log.info("many2:" + (manyStart - manyEnd));
	}

	private static final String[] portArray = new String[] { "8080", "8081", "8082" };

	/* 从文件中使用正则解析出url的部分信息，下面正则是将 以/开头的，非空白字符结尾的字符串取出，如“/abcc空格”字符串，\s为任意的空白符 */
	public static ArrayList<String> fetchUrlFromFile(String str) {
		ArrayList<String> arrayList = new ArrayList<String>();
		Pattern urlPattern = Pattern.compile("/[^\\s]+");
		// Pattern urlPattern = Pattern.compile("/[\\S]+");
		Matcher matcher = urlPattern.matcher(str);
		while (matcher.find()) {
			arrayList.add(matcher.group());
		}
		return arrayList;
	}

	public static void main(String[] args) throws Exception {
		CountDownLatch begin = new CountDownLatch(1);
		// StringBuilder stringBuilder = new StringBuilder();
		// String filePath = args[0];
		// FileReader fr = new FileReader(filePath);
		// BufferedReader br = new BufferedReader(fr);
		// while (br.ready()) {
		// stringBuilder.append(br.readLine());
		// }
		// String stringAll = stringBuilder.toString();
		// String stringAll = "http://localhost:8080/o2m-web/";
		// ArrayList<String> arrayList = fetchUrlFromFile(stringAll);
		// int allRequestSize = arrayList.size();
		// log.info("all request size is " + allRequestSize);
		// 设置最大的并发数量为60
		ExecutorService exec = Executors.newFixedThreadPool(60);

		CountDownLatch end = new CountDownLatch(1);
		// int i = 0;
		// for (String str : arrayList) {
		// for (int i = 0; i < args.length; i++) {
		// exec.execute(new CallHttpRequest(portArray[0], "/o2m-web/", begin,
		// end));
		//
		// }

		exec.execute(new Picture(begin, end));

		/*
		 * 如果想测试60个线程并发的访问,发配到同一台服务器上的两个tomcat，就用下面注释掉的代码 if (i % 2 == 0) {
		 * exec.execute(new CallHttpRequest(portArray[0], str, begin, end)); }
		 * else if (i % 2 == 1) { exec.execute(new CallHttpRequest(portArray[1],
		 * str, begin, end)); }
		 */
		// i++;
		// }
		long startTime = System.currentTimeMillis();
		// 当60个线程，初始化完成后，解锁，让六十个线程在4个双核的cpu服务器上一起竞争着跑，来模拟60个并发线程访问tomcat
		begin.countDown();

		try {
			end.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			log.info("all url requests is done!");
			log.info("the success size: " + CallHttpRequest.successRequest);
			log.info("the fail size: " + CallHttpRequest.failRequest);
			log.info("the timeout size: " + CallHttpRequest.timeOutRequest);
			// double successRate = (double) CallHttpRequest.successRequest /
			// allRequestSize;
			// log.info("the success rate is: " + successRate * 100 + "%");
			long endTime = System.currentTimeMillis();
			long costTime = endTime - startTime;
			log.info("the total time cost is: " + costTime + " ms");
			// log.info("every request time cost is: " + costTime /
			// allRequestSize + " ms");
		}
		exec.shutdown();
		log.info("main method end");

	}

}
