package com.test.service;

import com.test.entity.TestUser;
import com.test.entity.UserLog;

import java.util.List;

public interface TestUserService {

    public TestUser findUser();

    public void insertUser();

    public List<TestUser> findAllUser();

    public String insertDupliUser();

    void insertUserLog(UserLog userLog);
}
