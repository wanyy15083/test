package com.test.http;

import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;
import java.util.concurrent.ThreadFactory;

public class Utils {

    private Utils() {
    }

    public static void closeQuietly(CloseableHttpResponse response) {
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new ThreadFactory() {
            public Thread newThread(Runnable runnable) {
                Thread result = new Thread(runnable, name);
                result.setDaemon(daemon);
                return result;
            }
        };
    }

}
