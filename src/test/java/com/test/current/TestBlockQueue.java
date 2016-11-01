package com.test.current;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by songyigui on 2016/9/20.
 */
public class TestBlockQueue {

    private static final int invertal = 1000;
    private static final BlockingQueue<Task> queue = new LinkedBlockingQueue<Task>();
    private static final ExecutorService fetchEs = Executors.newSingleThreadExecutor();
    private static final ExecutorService handleEs = Executors.newFixedThreadPool(4);
//    private static final AtomicInteger count = new AtomicInteger();
//    private static final AtomicReference<String> starttime = new AtomicReference<String>(null);

    public static void main(String[] args) {
        Runnable fetch = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        List<Task> list = new ArrayList<Task>();
                        list.add(new Task());
                        list.add(new Task());
                        list.add(new Task());
                        list.add(new Task());
                        queue.addAll(list);
                        Thread.sleep(invertal);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        fetchEs.submit(fetch);
        fetchEs.shutdown();

        for (int i = 0; i < 4; i++) {
            Runnable handle = new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            if (queue.size() > 0) {
                                Task task = queue.take();
                                System.out.println("handle..." + Thread.currentThread().getName() + "..." + task.toString() + "..." + queue.size());
                            } else {
                                Thread.sleep(2000);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            handleEs.submit(handle);
        }
        handleEs.shutdown();
    }

}

