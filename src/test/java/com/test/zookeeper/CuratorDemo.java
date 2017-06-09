package com.test.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by songyigui on 2017/6/9.
 */
public class CuratorDemo {
    private CuratorFramework client;

    private static CountDownLatch latch = new CountDownLatch(2);
    private static ExecutorService es = Executors.newFixedThreadPool(2);


    public static final String ROOT = "/test";
    public static final String DATA = "123";
    public static final String CHILD = "/123";

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
        PathChildrenCache cache = new PathChildrenCache(client,ROOT+CHILD,true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

    }
}
