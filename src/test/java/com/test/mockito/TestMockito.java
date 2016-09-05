package com.test.mockito;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by songyigui on 2016/6/29.
 */
public class TestMockito {
    @Test
    public void testMockito() {
        //创建mock对象，可以是类和接口
        List<String> list = mock(List.class);

        //设置方法的预期返回值
        when(list.get(0)).thenReturn("helloworld");
        String result = list.get(0);

        //验证方法调用
        verify(list).get(0);

        //junit测试
        Assert.assertEquals("helloworld", result);


    }
}
