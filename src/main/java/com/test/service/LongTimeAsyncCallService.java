package com.test.service;

import com.test.callback.LongTermTaskCallback;

public interface LongTimeAsyncCallService {
    public void makeRemoteCallAndUnknownWhenFinish(LongTermTaskCallback callback);

    void asyncDoSth();

}
