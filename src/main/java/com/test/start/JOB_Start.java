package com.test.start;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;

public class JOB_Start {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("正在启动JOB服务......");
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		final ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring/spring-config.xml");
		ac.start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				ac.close();
			}
		});
		System.out.println("JOB服务已启动！");
		CountDownLatch countDownLatch = new CountDownLatch(1);
		countDownLatch.await();
	}
}
