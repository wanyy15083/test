package com.test.test;

import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class AsyncTask {
    @Async("asyncExecutor")
    public void deleteTemp(File file) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FileUtils.deleteQuietly(file);
    }
}