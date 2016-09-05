package com.test.entity;

import com.google.zxing.EncodeHintType;
import com.test.utils.DateUtils;
import com.test.utils.ImagesPathUtil;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class Picture implements Runnable {
	private static Logger log = Logger.getLogger(Picture.class);
	private CountDownLatch begin;
	private CountDownLatch end;

	public Picture(CountDownLatch begin, CountDownLatch end) {
		super();
		this.begin = begin;
		this.end = end;
	}

	@Override
	public void run() {
		try {
			begin.await();
			long manyStart = System.currentTimeMillis();

			log.info("manyStart:" + DateUtils.formatDateTime(new Date()));

			for (int i = 0; i < 5; i++) {
				long oneStart = System.currentTimeMillis();

				log.info("oneStart:" + DateUtils.formatDateTime(new Date()));

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

				log.info("oneEnd:" + DateUtils.formatDateTime(new Date()));
				log.info("one:" + (oneStart - oneEnd));

			}
			long manyEnd = System.currentTimeMillis();

			log.info("manyEnd:" + DateUtils.formatDateTime(new Date()));
			log.info("many:" + (manyStart - manyEnd));
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		} finally {
			end.countDown();
		}
	}
}
