package com.test.http;

import org.apache.http.conn.HttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
 * 定期清理无效的http连接
 */
public class IdleConnectionEvictor extends Thread {

    private final HttpClientConnectionManager connMgr;

    private long waitTime;

    private volatile boolean shutdown;

    public IdleConnectionEvictor(HttpClientConnectionManager connMgr, long waitTime) {
        this.connMgr = connMgr;
        this.waitTime = waitTime;
//        this.start();
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(waitTime);
                    // 关闭失效的连接
                    connMgr.closeExpiredConnections();
                    // 可选，关闭超过某时间的空闲连接
                    connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                    System.out.println(Thread.currentThread().getName() + " close connection!");
                }
            }
        } catch (InterruptedException ex) {
            // 结束
        }
    }

    /**
     * 销毁释放资源
     */
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}
