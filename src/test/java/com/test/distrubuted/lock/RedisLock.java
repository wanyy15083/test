package com.test.distrubuted.lock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by songyigui on 2017/6/14.
 */
public class RedisLock {
    private Jedis jedis;//非切片额客户端连接
    private JedisPool jedisPool;//非切片连接池
    private ShardedJedis shardedJedis;//切片额客户端连接
    private ShardedJedisPool shardedJedisPool;//切片连接池

    public RedisLock() {
        initialPool();
//        initialShardedPool();
//        shardedJedis = shardedJedisPool.getResource();
        jedis = jedisPool.getResource();

    }

    /**
     * 初始化非切片池
     */
    private void initialPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(false);

        jedisPool = new JedisPool(config, "127.0.0.1", 6379);
    }

    /**
     * 初始化切片池
     */
    private void initialShardedPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(false);
        // slave链接
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        shards.add(new JedisShardInfo("127.0.0.1", 6379, "master"));

        // 构造池
        shardedJedisPool = new ShardedJedisPool(config, shards);
    }

    /**
     * 获取锁
     *
     * @param lockId
     * @return
     */
    public boolean lock(String lockId) {
//        while (true) {
            Long flag = jedis.setnx(lockId, "1");
            if (flag == 1) {
                System.out.println(Thread.currentThread().getName() + " get lock...");
                return true;
            }
            System.out.println(Thread.currentThread().getName() + " is trying lock...");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
//        }
        return false;
    }

    /**
     * 超时获取锁
     *
     * @param lockId
     * @param timeout
     * @return
     */
    public boolean lock(String lockId, long timeout) {
        long current = System.currentTimeMillis();
        long future = current + timeout;
        long timeStep = 500;
        CountDownLatch latch = new CountDownLatch(1);
        while (future > current) {
            Long flag = jedis.setnx(lockId, "1");
            if (flag == 1) {
                System.out.println(Thread.currentThread().getName() + " get lock...");
                return true;
            }
            System.out.println(Thread.currentThread().getName() + " is trying lock...");

            try {
                latch.await(timeStep, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            current = current + timeStep;
        }
        return false;
    }

    public void unlock(String lockId) {
        Long flag = jedis.del(lockId);
        if (flag > 0) {
            System.out.println(Thread.currentThread().getName() + " release lock...");
        } else {
            System.out.println(Thread.currentThread().getName() + " release lock fail...");
        }
    }

    public static class MyThreadFactory implements ThreadFactory {
        public static AtomicInteger count = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            count.getAndIncrement();
            Thread t = new Thread(r);
            t.setName("Thread-lock-test" + count);
            return t;
        }
    }


    public static class Task implements Runnable {
        final String lockId = "test1";

        @Override
        public void run() {
            RedisLock redisLock = new RedisLock();
            redisLock.lock(lockId);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            redisLock.unlock(lockId);
        }
    }

    public static void main(String[] args) {
        MyThreadFactory factory = new MyThreadFactory();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 3; i++) {
            service.execute(factory.newThread(new Task()));
        }
    }
}
