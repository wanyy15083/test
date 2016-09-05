package com.test.test;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试监控类
 * 
 * @author
 * 
 */
public class WatchThread {

	private String name = UUID.randomUUID().toString();

	/**
	 * 测试函数
	 * 
	 * @throws InterruptedException
	 */
	public void testThread() throws InterruptedException {
		int threadNum = 10;
		// 初始化countDown
		CountDownLatch threadSignal = new CountDownLatch(threadNum);
		// 创建固定长度的线程池
		// Executor executor = Executors.newFixedThreadPool(threadNum);
		// 此处不可以用接口 需要使用Executor的实现类 ExecutorService Executor未提供shutdown等方法
		ExecutorService executor = Executors.newFixedThreadPool(threadNum);
		for (int i = 0; i < threadNum; i++) { // 开threadNum个线程
			Runnable task = new TestThread(threadSignal);
			// 执行
			executor.execute(task);

		}
		threadSignal.await(); // 等待所有子线程执行完
		// 固定线程池执行完成后 将释放掉资源 退出主进程
		executor.shutdown();// 并不是终止线程的运行，而是禁止在这个Executor中添加新的任务
		// do work end
		// 退出主进程
		System.out.println(Thread.currentThread().getName() + "+++++++结束.");
	}

	/**
	 * 测试函数
	 */
	public static void main(String[] args) throws InterruptedException {
		WatchThread test = new WatchThread();
		test.testThread();
	}

	/**
	 * 
	 * @author wangmuming 此可以做完内部类 也可以不做未内部类 作为内部类的时候 有一个好处 就是可以直接引用给类的主对象的成员变量
	 *         如此处的name 当然
	 */
	private class TestThread implements Runnable {
		private CountDownLatch threadsSignal;

		public TestThread(CountDownLatch threadsSignal) {
			this.threadsSignal = threadsSignal;
		}

		public void run() {
			System.out.println(Thread.currentThread().getName() + "开始..." + name);
			System.out.println("开始了线程：：：：" + threadsSignal.getCount());

			// do shomething

			// 核心处理逻辑

			// 用到成员变量name作为参数

			// 线程结束时计数器减1
			threadsSignal.countDown();// 必须等核心处理逻辑处理完成后才可以减1
			System.out.println(Thread.currentThread().getName() + "结束. 还有" + threadsSignal.getCount() + " 个线程");
		}
	}

}
