package com.test.callback;

import java.util.concurrent.TimeUnit;

/**
 * Created by songyigui on 2017/1/13.
 */
public class Remote {
    public void executeMessage(String msg, Callback callback) {

        System.out.println("处理消息~~~远程！！！" + msg);
        try {
            //do sth...
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("处理完成~~~远程！！！");
        callback.call();
    }
}
