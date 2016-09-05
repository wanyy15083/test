package com.test.test;

import com.google.zxing.EncodeHintType;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class QRCodeService {
	public static void main(String[] args) throws Exception {
		// for(String arg:args){
		// ByteArrayOutputStream out =
		// QRCode.from(arg).to(ImageType.PNG).stream();
		// try
		// {
		// FileOutputStream fout = new FileOutputStream(new
		// File("D:/log/"+arg+".jpg"));
		// fout.write(out.toByteArray());
		// fout.flush();
		// fout.close();
		// }
		// catch (FileNotFoundException e)
		// {
		// e.printStackTrace();
		// }
		// catch (IOException e)
		// {
		// e.printStackTrace();
		// }
		// }
		// String qrcodeContent = "https://www.baidu.com/";
		// for(int i=0;i<=20;i++){
		// String qrcodeContent
		// ="http://www.laigome.com/store/item?storeCode=A46W&skuId=100001487";
		// // String qrcodeContent
		// ="http://localhost:8080?storeCode=A001&skuId=100210214&abc=232";
		// // QRCode code =
		// QRCode.from(qrcodeContent).to(ImageType.PNG).withSize(250, 250);
		// // ByteArrayOutputStream out = code.stream();
		// QRCode code =
		// QRCode.from(qrcodeContent).to(ImageType.JPG).withHint(EncodeHintType.MARGIN,
		// 0).withSize(10230,10230);
		//
		// try
		// {

		// BufferedOutputStream bufo = new BufferedOutputStream(new
		// FileOutputStream(new File("D:/log/QR_sku"+i+".jpg")));
		// // FileOutputStream fout = new FileOutputStream(new
		// File("D:/log/QR_sku"+i+".jpg"));
		// // code.writeTo(bufo);
		// ByteArrayOutputStream stream = code.stream();
		// BufferedOutputStream bo = new BufferedOutputStream(stream);
		// byte[] qByte = new byte[1024];
		// bufo.flush();
		// bufo.close();
		// bufo = null;
		// System.gc();

//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
//		QRCode code = null;
//		BufferedOutputStream bufo = null;
//		System.out.println(new Date());
//		byte[] tr = new byte[100000*100000];
//		for (int i = 0; i < 100000*100000; i++) {
//			tr[i] = (byte) 1;
//		}
//		System.out.println(new Date());
		
//		for (int i = 0; i < 5; i++) {
//			System.out.println(new Date());
//			System.out.println(i +" start");
			String qrcodeContent = "http://www.laigome.com/store/item?storeCode=A46W&skuId=100001487";
//			 String qrcodeContent
//			 ="http://localhost:8080?storeCode=A001&skuId=100210214&abc=232";
//			 QRCode code = QRCode.from(qrcodeContent).to(ImageType.PNG).withSize(250, 250);
//			 ByteArrayOutputStream out = code.stream();
//			 QRCode code = new
//			stream.reset();
			for (int i = 0; i <= 100; i++) {
				
				QRCode code = QRCode.from(qrcodeContent).to(ImageType.JPG).withHint(EncodeHintType.MARGIN, 0).withSize(99*i, 99*i);
//				ByteArrayOutputStream stream = code.stream();
//			byte[] array = stream.toByteArray();
				BufferedOutputStream bufo = new BufferedOutputStream(new FileOutputStream(new File("D:/log/qr/QR_sku" + i + ".jpg")));
				code.writeTo(bufo);
//			bufo.write(array);
				bufo.flush();
				bufo.close();
				bufo = null;
			}
//			 code = null;
//				System.out.println(i +" end");
//				System.out.println(new Date());
//
//		}
//		   CountDownLatch countDownLatch = new CountDownLatch(1);
//	        countDownLatch.await();
	}
}