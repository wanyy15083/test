package com.test.concurrent;

public class AccountingSync implements Runnable{
    static AccountingSync instance = new AccountingSync();
    static int i = 0;

//    public synchronized void increase() {
//        i++;
//    }

    // 加上static相当于请求类的锁，所以没问题
    public synchronized static void increase() {
        i++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 10000000; j++) {
            increase();

    //            synchronized (instance) {
    //                i++;
    //            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AccountingSync());
        Thread t2 = new Thread(new AccountingSync());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }

}
