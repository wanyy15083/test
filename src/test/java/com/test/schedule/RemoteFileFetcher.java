package com.test.schedule;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by songyigui on 2017/6/21.
 */
public class RemoteFileFetcher {
    private static final Logger LOGGER = LogManager.getLogger(TestScheduleExecutor.class);

    private byte[] fileContent;
    private String url;
    private long lastModified;
    private int connectTimeout;
    private int readTimeout;
    private FileChangeListener listener;

    private static ScheduledExecutorService schedule = Executors.newScheduledThreadPool(4, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "RemoteFileFetcher_Schedule_Thread");
        }
    });


    public RemoteFileFetcher(String url, int reloadInterval, FileChangeListener listener) {
        this.url = url;
        this.listener = listener;
        this.connectTimeout = 3000;
        this.readTimeout = 3000;
        if (reloadInterval > 0) {
            schedule.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    RemoteFileFetcher.this.doFetch();
                }
            }, reloadInterval, reloadInterval, TimeUnit.MILLISECONDS);
        }
    }

    private void doFetch() {
        if (url == null)
            return;
        LOGGER.info("Begin fetch remote file... url={}", this.url);
        try {
            URL target = new URL(this.url);
            this.fileContent = IOUtils.toByteArray(target);
            this.lastModified = System.currentTimeMillis();
            if (this.listener != null && this.fileContent != null) {
                this.listener.fileReloaded(this.fileContent);
            }
        } catch (Exception e) {
            LOGGER.error("read from url failed", e);
        }
    }

    public static RemoteFileFetcher createPeriodFetcher(String url, int reloadInterval, FileChangeListener listener) {
        return new RemoteFileFetcher(url, reloadInterval, listener);
    }

    public interface FileChangeListener {
        public void fileReloaded(byte[] contentBytes) throws IOException;
    }
}
