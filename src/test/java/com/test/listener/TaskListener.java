package com.test.listener;

/**
 * Created by songyigui on 2018/1/11.
 */
public interface TaskListener {

    void onStart(TaskEvent event);
    void onComplete(TaskEvent event);
    void onError(TaskEvent event);
}
