package com.test.callback;

/**
 * Created by songyigui on 2017/1/13.
 */
public abstract class Task {

    /**
     * 执行回调
     * @param callback
     */
    public final void executeWith(Callback callback){
        execute();
        if (callback != null) {
            callback.call();
        }
    }

    public abstract void execute();
}
