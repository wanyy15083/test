package com.test.distuptor;

import com.gome.o2m.ic.scancode.model.GoodsTwoCode;
import com.lmax.disruptor.EventFactory;

/**
 * Created by songyigui on 2016/11/8.
 */
public class QrCodeEventFactory implements EventFactory<GoodsTwoCode> {
    @Override
    public GoodsTwoCode newInstance() {
        return new GoodsTwoCode();
    }
}
