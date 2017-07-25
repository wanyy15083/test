package com.test.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 默认server.xml
 * 10个并发 100次循环，550左右
 * 5个并发 100次循环，400左右
 * 1个并发，100次循环，600左右
 * 10个并发 500次循环，550左右
 * 5个并发 500次循环，2500左右
 * 1个并发，500次循环，1500左右
 * 10个并发 1000次循环，3500左右
 * 5个并发 1000次循环，1800左右
 * 1个并发，1000次循环，3900左右
 * 自定义server.xml
 * 10个并发 100次循环，200左右
 * 5个并发 100次循环，200左右
 * 1个并发，100次循环，500左右
 * 10个并发 500次循环，1000左右
 * 5个并发 500次循环，900左右
 * 1个并发，500次循环，2300左右
 * 10个并发 1000次循环，3200左右
 * 5个并发 1000次循环，3300左右
 * 1个并发，1000次循环，5000左右
 * jdk1.5
 */
public class RequestServer {

    //请求地址
    public static final String REQ_URL = "http://192.168.10.10:8888/httpweb/hello";
    //        public static final String REQ_URL = "http://127.0.0.1:8888/hello";
    public static final int MAX_TOTAL = 200;
    public static final int DEFAULT_MAX_PER_ROUTE = 200;
    public static final int CONNECT_TIMEOUT = 30000;
    public static final int CONNECTION_REQUEST_TIMEOUT = 5000;
    public static final int SOCKET_TIMEOUT = 2000;
    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final int COUNT = 5000;
    public static final int THREADS_NUM = 10;
    private static ExecutorService executors = Executors.newFixedThreadPool(THREADS_NUM);
    private static CountDownLatch latch = new CountDownLatch(COUNT);

    private static Executor executor = new ThreadPoolExecutor(0 /* corePoolSize */,
            Integer.MAX_VALUE /* maximumPoolSize */, 60L /* keepAliveTime */, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), Utils.threadFactory("HttpClient ConnectionPool", true));

    public static void main(String[] args) throws Exception {
        //连接池
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAX_TOTAL);// 连接池最大连接数
        connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);//每个主机地址的并发数


        //请求配置
        final RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(CONNECT_TIMEOUT)//创建连接最长时间
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)//连接池获取连接最长时间
                .setSocketTimeout(SOCKET_TIMEOUT)//数据传输的最长时间
                .build();

        final CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
//        final CloseableHttpClient httpClient = HttpClients.createDefault();

        executor.execute(new IdleConnectionEvictor(connectionManager, 5000));

        long start = System.currentTimeMillis();

        for (int i = 0; i < COUNT; i++) {
            executors.execute(new GetRunnable(latch, config, httpClient));
//            executors.execute(new PostRunnable(latch, config, httpClient));
        }
        latch.await();

//        executors.shutdown();

//        for (int i = 0; i < 100; i++) {
//            new Thread(new Runnable() {
//                public void run() {
//                    try {
//                        System.out.println(Thread.currentThread().getName()+" get");
//                        doGet(null, httpClient);
//                    } catch (Exception e) {
//                    }
//                }
//            }).start();
//        }


//        doGet(config, httpClient);
//        doGet(null, httpClient);
//        doPost(config,httpClient);
        System.out.println("total-total:" + (System.currentTimeMillis() - start));

    }


    public static class GetRunnable implements Runnable {
        private CountDownLatch latch;
        private RequestConfig config;
        private CloseableHttpClient httpClient;

        public GetRunnable(CountDownLatch latch, RequestConfig config, CloseableHttpClient httpClient) {
            this.latch = latch;
            this.config = config;
            this.httpClient = httpClient;
        }

        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " get");
                long start = System.currentTimeMillis();
                doGet(config, httpClient);
                System.out.println("total:" + (System.currentTimeMillis() - start));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }

    public static class PostRunnable implements Runnable {
        private RequestConfig config;
        private CloseableHttpClient httpClient;

        public PostRunnable(CountDownLatch latch, RequestConfig config, CloseableHttpClient httpClient) {
            this.config = config;
            this.httpClient = httpClient;
        }

        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " post");
                long start = System.currentTimeMillis();
                doPost(config, httpClient);
                System.out.println("total:" + (System.currentTimeMillis() - start));

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }

    //get请求
    public static void doGet(RequestConfig config, CloseableHttpClient httpClient) throws Exception {
        CloseableHttpResponse response = null;
        try {
            URI uri = new URIBuilder(REQ_URL).setParameter("name", "abc123中国").build();
            HttpGet httpGet = new HttpGet(uri);
            if (config != null)
                httpGet.setConfig(config);
            response = httpClient.execute(httpGet);
            String body = "";
            if (response.getEntity() != null) {
                body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
            } else {
                body = response.getStatusLine().toString();
            }
            System.out.println(new String(body.getBytes("ISO-8859-1"), "UTF-8"));
            EntityUtils.consume(response.getEntity());
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            Utils.closeQuietly(response);
        }
    }

    //post请求
    public static void doPost(RequestConfig config, CloseableHttpClient httpClient) throws Exception {
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(REQ_URL);
            if (config != null)
                httpPost.setConfig(config);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                sb.append('a');
            }
            nvps.add(new BasicNameValuePair("name", sb.toString()));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, DEFAULT_CHARSET);
            httpPost.setEntity(formEntity);
            response = httpClient.execute(httpPost);
            String body = "";
            if (response.getEntity() != null) {
                body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
            } else {
                body = response.getStatusLine().toString();
            }
            System.out.println(body);
            EntityUtils.consume(response.getEntity());
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            Utils.closeQuietly(response);
        }
    }
}
