package com.test.test;

import com.test.entity.TestUser;
import com.test.service.TestUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试基类（无事务回滚）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring-config.xml"})
public class UserTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private TestUserService testUserService;

    @Test
    public void testTrans(){
//        String s = testUserService.insertDupliUser();
//        TestUser user = testUserService.findUser(1);
        TestUser testUser = new TestUser();
        testUser.setId(null);
        testUser.setName("小明");
        testUser.setAge(18);
        testUser.setAddress("北京");
        testUser.setTelephone("134XXXXXXXXXX");
        testUserService.insertUser(testUser);
//        System.out.println(user);

    }
}

