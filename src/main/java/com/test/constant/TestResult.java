package com.test.constant;

import com.test.base.BaseResult;

/**
 * test系统常量枚举类
 * Created by songyigui on 2017/6/20.
 */
public class TestResult extends BaseResult {
    public TestResult(TestResultConstant testResultConstant, Object data) {
        super(testResultConstant.getCode(), testResultConstant.getMessage(), data);
    }

}
