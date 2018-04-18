package com.test.service;

import com.test.entity.TestUser;
import com.test.entity.UserLog;

import java.util.List;

public interface TestUserService {

    public TestUser findUser(Integer id);

    public void insertUser(TestUser user);

    public List<TestUser> findAllUser();

    public String insertDupliUser();

    void insertUserLog(UserLog userLog);

}
