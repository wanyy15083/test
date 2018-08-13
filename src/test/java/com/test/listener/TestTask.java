package com.test.listener;

import com.weshare.sdk.util.*;

/**
 * Created by songyigui on 2018/1/11.
 */
public class TestTask {
    public static void main(String[] args) throws Exception {
        Task task = new Task(Utility.generateUUID(), "test");
        TaskContext context = new TaskContext(task);
        context.addListener(new TaskListener() {
            @Override
            public void onStart(TaskEvent event) {
                System.out.println("task start:" + event);
            }

            @Override
            public void onComplete(TaskEvent event) {
                System.out.println("task complete:" + event);
            }

            @Override
            public void onError(TaskEvent event) {
                System.out.println("task error:" + event);
            }
        });

        context.start();
        context.complete();
        context.error("test error");
    }
}
