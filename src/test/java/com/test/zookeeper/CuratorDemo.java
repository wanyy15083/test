package com.test.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.TestPropertySource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by songyigui on 2017/6/9.
 */
public class CuratorDemo {
    private CuratorFramework client;
    private static DistributedBarrier barrier;


    private static CountDownLatch latch = new CountDownLatch(2);
    private static ExecutorService es = Executors.newFixedThreadPool(2);


    public static final String ROOT = "/test";
    public static final String DATA = "123";
    public static final String CHILD = "/123";
    public static final String MASTER = "/master";
    public static final String LOCK = "/lock";

    @Before
    public void init() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
//        client = CuratorFrameworkFactory.newClient("127.0.0.1:2081", 5000, 3000, retryPolicy);
        client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
    }

    @Test
    public void testCreate() throws Exception {
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath(ROOT + CHILD, "init".getBytes());
    }

    @Test
    public void testDelete() throws Exception {
        Stat stat = new Stat();
        byte[] bytes = client.getData().storingStatIn(stat).forPath(ROOT + CHILD);
        System.out.println(new String(bytes));
        client.delete().deletingChildrenIfNeeded()
                .withVersion(stat.getVersion()).forPath(ROOT + CHILD);

    }

    @Test
    public void testGetData() throws Exception {
        Stat stat = new Stat();
        System.out.println(new String(client.getData().storingStatIn(stat).forPath(ROOT + CHILD)));

    }

    @Test
    public void updateData() throws Exception {
        Stat stat = new Stat();
        byte[] bytes = client.getData().storingStatIn(stat).forPath(ROOT + CHILD);
        System.out.println(new String(bytes));
        client.setData().withVersion(stat.getVersion()).forPath(ROOT + CHILD);
        Stat stat1 = new Stat();
        System.out.println(new String(client.getData().storingStatIn(stat1).forPath(ROOT + CHILD)));
    }

    @Test
    public void testBackGround() throws Exception {
        System.out.println("main:" + Thread.currentThread().getName());
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println("event[code:" + curatorEvent.getResultCode() + ",type:" + curatorEvent.getType() + "]");
                System.out.println(Thread.currentThread().getName());
                latch.countDown();
            }
        }, es).forPath(ROOT + CHILD, "init".getBytes());

        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println("event[code:" + curatorEvent.getResultCode() + ",type:" + curatorEvent.getType() + "]");
                System.out.println(Thread.currentThread().getName());
                latch.countDown();
            }
        }).forPath(ROOT + CHILD, "init".getBytes());

        latch.await();
        es.shutdown();
    }

    @Test
    public void testNodeCache() throws Exception {
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(ROOT + CHILD, "init".getBytes());
        final NodeCache cache = new NodeCache(client, ROOT + CHILD, false);
        cache.start(true);
        cache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("Node data update,new data:" + new String(cache.getCurrentData().getData()));
            }
        });
        client.setData().forPath(ROOT + CHILD, "u".getBytes());
        Thread.sleep(1000);
        client.delete().deletingChildrenIfNeeded().forPath(ROOT + CHILD);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void testPathChildrenCache() throws Exception {
        PathChildrenCache cache = new PathChildrenCache(client, ROOT, true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED," + event.getData());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED," + event.getData());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED," + event.getData());
                        break;
                    default:
                        break;
                }
            }
        });

        client.create().withMode(CreateMode.PERSISTENT).forPath(ROOT);
        Thread.sleep(1000);
        client.create().withMode(CreateMode.PERSISTENT).forPath(ROOT + CHILD);
        Thread.sleep(1000);
        client.delete().forPath(ROOT + CHILD);
        Thread.sleep(1000);
        client.delete().forPath(ROOT);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void testMasterSelect() throws InterruptedException {
        LeaderSelector selector = new LeaderSelector(client, MASTER, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("be master");
                Thread.sleep(3000);
                System.out.println("complete master,release");
            }
        });
        selector.autoRequeue();
        selector.start();
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void testLock() throws InterruptedException {
        final InterProcessMutex lock = new InterProcessMutex(client, LOCK);
        final CountDownLatch down = new CountDownLatch(1);
        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        lock.acquire();
                        down.await();
                    } catch (Exception e) {
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|sss");
                    String orderNo = sdf.format(new Date());
                    System.out.println("generate orderNo:" + orderNo);
                    try {
                        lock.release();
                    } catch (Exception e) {
                    }
                }
            }).start();
        }
        down.countDown();

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void testDistAtomic() throws Exception {
        DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(client, LOCK, new RetryNTimes(3, 1000));
        AtomicValue<Integer> atomicValue = atomicInteger.add(8);
        System.out.println(atomicValue.succeeded());
    }

    @Test
    public void testBrrier() throws Exception {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    barrier = new DistributedBarrier(client, "/barrier");
                    System.out.println(Thread.currentThread().getName() + "号barrier设置");
                    try {
                        barrier.setBarrier();
                        barrier.waitOnBarrier();
                        System.out.println("启动。。。");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
        Thread.sleep(2000);
        barrier.removeBarrier();
    }

    @Test
    public void testBarrier2() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(client, "/barrier", 5);
                        Thread.sleep(Math.round(Math.random() * 3000));
                        System.out.println(Thread.currentThread().getName() + "号进入barrier");
                        barrier.enter();
                        System.out.println("启动...");
                        Thread.sleep(Math.round(Math.random() * 3000));
                        barrier.leave();
                        System.out.println("退出...");
                    } catch (Exception e) {
                    }

                }
            }).start();
        }

        Thread.sleep(Integer.MAX_VALUE);
    }

}
