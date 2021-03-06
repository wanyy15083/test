package com.test.controller;

import com.test.entity.TestUser;
import com.test.service.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by songyigui on 2016/8/8.
 */
@Controller
@RequestMapping("user")
public class TestUserController {
    @Autowired
    private TestUserService testUserService;


    @RequestMapping("insert")
    public String insertUser() {
        String reuslt = testUserService.insertDupliUser();
        return reuslt;
    }

    @RequestMapping("find")
    @ResponseBody
    public TestUser findUser() {
        return testUserService.findUser(1);
    }

}
