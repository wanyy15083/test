package com.test.test;

import com.google.zxing.EncodeHintType;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Test1 {

	public static void main(String[] args) throws Exception {
//		QRCode code = null;
//		BufferedOutputStream bufo = null;
//		for (int i = 0; i < 5; i++) {
//			System.out.println(new Date());
//			System.out.println(i + " start");
//			String qrcodeContent = "http://www.laigome.com/store/item?storeCode=A46W&skuId=100001487";
//			code = QRCode.from(qrcodeContent).to(ImageType.JPG).withHint(EncodeHintType.MARGIN, 0).withSize(8250, 8250);
//			bufo = new BufferedOutputStream(new FileOutputStream(new File("D:/log/QR_sku" + i + ".jpg")));
//			code.writeTo(bufo);
//			bufo.flush();
//			bufo.close();
//			bufo = null;
//			code = null;
//			System.out.println(i + " end");
//			System.out.println(new Date());
//
//		}
//		CountDownLatch countDownLatch = new CountDownLatch(1);
//		countDownLatch.await();
		List<Integer> list = new ArrayList<Integer>();
//		list.add(1);
//		list.add(2);
//		list.add(3);
//		list.add(1);
		for (Integer integer : list) {
			System.out.println(integer);
		}
	}
	

}
