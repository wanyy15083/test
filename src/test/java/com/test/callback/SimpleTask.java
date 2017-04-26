package com.test.callback;

import java.util.concurrent.TimeUnit;

/**
 * Created by songyigui on 2017/1/13.
 */
public class SimpleTask extends Task {
    @Override
    public void execute() {
        System.out.println("扫地");
        Thread thread = new Thread();
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
