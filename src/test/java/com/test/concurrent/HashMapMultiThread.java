package com.test.concurrent;

import java.util.*;

/**
 * Created by bj-s2-w1631 on 18-8-13.
 */
public class HashMapMultiThread {
    static HashMap<String, String> map = new HashMap<>();
    public static class AddThread implements Runnable {
        int start = 0;

        public AddThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            for (int i = start; i < 100000; i+=2) {
                map.put(Integer.toString(i), Integer.toBinaryString(i));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 =  new Thread(new HashMapMultiThread.AddThread(0));
        Thread t2 =  new Thread(new HashMapMultiThread.AddThread(1));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(map.size());
    }
}
