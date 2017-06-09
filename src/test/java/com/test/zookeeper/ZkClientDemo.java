package com.test.zookeeper;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by songyigui on 2017/6/9.
 */
public class ZkClientDemo {

    private ZkClient zkClient;

    public static final String ROOT = "/test";
    public static final String DATA = "123";
    public static final String CHILD = "/123";

    @Before
    public void init() {
        zkClient = new ZkClient("127.0.0.1:2181", 5000);
    }

    @Test
    public void testCreate() {
        zkClient.createPersistent(ROOT + CHILD, true);
//        List<String> children = zkClient.getChildren(ROOT);
//        System.out.println(children.toString());
    }

    @Test
    public void testDelete() {
        zkClient.delete(ROOT + CHILD);
    }

    @Test
    public void testGet() throws InterruptedException {
        zkClient.subscribeChildChanges(ROOT + CHILD, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println(parentPath + "'s child changed,currentChilds" + currentChilds);
            }
        });

        zkClient.createPersistent(ROOT, true);
        Thread.sleep(1000);
        System.out.println(zkClient.getChildren(ROOT));
        Thread.sleep(1000);
        zkClient.createPersistent(ROOT + CHILD);
        Thread.sleep(1000);
        zkClient.delete(ROOT + CHILD);
        Thread.sleep(1000);
        zkClient.delete(ROOT);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void testGetData() throws InterruptedException {
        zkClient.createEphemeral(ROOT, DATA);
        zkClient.subscribeDataChanges(ROOT, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("Node " + dataPath + " changed,new data:" + data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("Node " + dataPath + " deleted.");
            }
        });
        System.out.println(zkClient.readData(ROOT));
        zkClient.writeData(ROOT, "456");
        Thread.sleep(1000);
        zkClient.delete(ROOT);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
