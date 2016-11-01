package com.test.current;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by songyigui on 2016/9/19.
 */
public class TestConcurrent {

    private static final AtomicInteger count = new AtomicInteger();
    private static final ExecutorService proExec = Executors.newSingleThreadExecutor();
    private static final ExecutorService conExec = Executors.newFixedThreadPool(4);
    private static final BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

    public static void main(String[] args) {

//        Producer producer = new Producer();
//        Consumer consumer = new Consumer();
//        conExec.submit(producer);
//        conExec.submit(consumer);
//        conExec.execute(new Producer());
//        conExec.execute(new Consumer());
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        conExec.shutdown();
//        conExec.shutdownNow();


//        List<Task> buffer = new ArrayList<Task>(Constants.MAX_BUFFER_SIZE);
        BlockingQueue<Task> buffer = new LinkedBlockingQueue<Task>(Constants.MAX_BUFFER_SIZE);
        ExecutorService es = Executors.newFixedThreadPool(Constants.NUM_OF_PRODUCER + Constants.NUM_OF_CONSUMER);
        for (int i = 1; i <= Constants.NUM_OF_PRODUCER; i++) {
            es.execute(new Producer(buffer));
        }
        for (int i = 1; i <= Constants.NUM_OF_CONSUMER; i++) {
            es.execute(new Consumer(buffer));
        }


    }


}

class Constants {
    public static final int MAX_BUFFER_SIZE = 10;
    public static final int NUM_OF_PRODUCER = 2;
    public static final int NUM_OF_CONSUMER = 4;
}

class Task {
    private String id;

    public Task() {
        id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Task{");
        sb.append("id='").append(id).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

class Producer implements Runnable {
    //    private List<Task> buffer;
//
//    public Producer(List<Task> buffer) {
//        this.buffer = buffer;
//    }
    private BlockingQueue<Task> buffer;

    public Producer(BlockingQueue<Task> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
//            synchronized (buffer) {
//                while (buffer.size() >= Constants.MAX_BUFFER_SIZE) {
//                    try {
//                        buffer.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Task task = new Task();
//                    buffer.add(task);
//                    buffer.notify();
//                    buffer.notifyAll();
//                }
//            }
            try {
                Task task = new Task();
                buffer.put(task);
                System.out.println("Producer[" + Thread.currentThread().getName() + "]put" + task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

class Consumer implements Runnable {
    //    private List<Task> buffer;
//
//    public Consumer(List<Task> buffer) {
//        this.buffer = buffer;
//    }
    private BlockingQueue<Task> buffer;

    public Consumer(BlockingQueue<Task> buffer) {
        this.buffer = buffer;
    }


    @Override
    public void run() {
        while (true) {
//            synchronized (buffer) {
//                while (buffer.isEmpty()) {
//                    try {
//                        buffer.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Task task = buffer.remove(0);
//                    buffer.notifyAll();
//                    System.out.println("Consumer[" + Thread.currentThread().getName() + "]get" + task);
//                }
//            }
            try {
                Task task = buffer.take();
                System.out.println("Consumer[" + Thread.currentThread().getName() + "]get" + task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//class DataHandler {
//    private static final BlockingQueue<String> data = new ArrayBlockingQueue<String>(10);
//    private static final AtomicInteger count = new AtomicInteger();
//
//    public static void produce() throws InterruptedException {
//        data.put("data" + count.incrementAndGet());
//    }
//
//    public static String consume() throws InterruptedException {
//        String result = data.take();
//        return result;
//    }
//
//    public static int getSize() {
//        return data.size();
//    }
//}
//
//class Producer implements Runnable {
//
//    @Override
//    public void run() {
//        try {
//            while (true) {
//                System.out.println("生产数据开始...:" + System.currentTimeMillis());
//                DataHandler.produce();
//                System.out.println("生产数据结束...:" + System.currentTimeMillis());
//                System.out.println("生产剩余数量...:" + DataHandler.getSize());
//                Thread.sleep(3000);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//
//class Consumer implements Runnable {
//
//    @Override
//    public void run() {
//        try {
//            while (true) {
//                System.out.println("消费数据开始...:" + System.currentTimeMillis());
//                DataHandler.consume();
//                System.out.println("消费数据结束...:" + System.currentTimeMillis());
//                System.out.println("消费剩余数量...:" + DataHandler.getSize());
//                Thread.sleep(3000);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}