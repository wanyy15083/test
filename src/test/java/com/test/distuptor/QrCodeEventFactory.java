package com.test.distuptor;

import com.lmax.disruptor.EventFactory;
import com.test.entity.GoodsTwoCode;

/**
 * Created by songyigui on 2016/11/8.
 */
public class QrCodeEventFactory implements EventFactory<GoodsTwoCode> {
    @Override
    public GoodsTwoCode newInstance() {
        return new GoodsTwoCode();
    }
}
