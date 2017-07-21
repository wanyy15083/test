package com.test.service.impl;

import com.test.entity.TestUser;
import com.test.entity.UserLog;
import com.test.mapper.TestUserMapper;
import com.test.service.TestUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestUserServiceImpl implements TestUserService {
    private final static Logger logger = LogManager.getLogger(TestUserServiceImpl.class);


    @Autowired
    private TestUserMapper testUserMapper;
//    @Autowired
//    private SkuService skuService;


//    @Autowired
//    private DataSourceTransactionManager transactionManager;

    public TestUser findUser() {
//        Sku sku = skuService.get(1);
//        System.out.println(sku);
        Integer id = 1;
        TestUser testUser = testUserMapper.selectByPrimaryKey(id);
        logger.info("user.name:" + testUser.getName() + "user.address"
                + testUser.getAddress());
        return testUser;
    }

    public void insertUser() {
        TestUser testUser = new TestUser();
        testUser.setId(null);
        testUser.setName("小明");
        testUser.setAge(18);
        testUser.setAddress("北京");
        testUser.setTelephone("134XXXXXXXXXX");
        testUserMapper.insert(testUser);
        logger.info("user.insert.success");

        System.out.println("111");
    }

    @Override
    public List<TestUser> findAllUser() {
        return testUserMapper.selectAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertDupliUser() {
//        try {
//            AnnotationTransactionAspect.aspectOf().setTransactionManager(transactionManager);
        TestUser user1 = new TestUser(1, "110022", 20, "China", "小明");
        TestUser user2 = new TestUser(null, "110022", 20, "China", "小明");
        TestUser user3 = new TestUser(null, "110023", 20, "China", "小张");
        testUserMapper.insert(user2);
        insertUser(null, user3);
//        int a = 1/0;
//            testUserMapper.insert(user2);
//            if (user1 != null) {
//                testUserMapper.insert(user1);
//            }
        return "true";

    }

    @Override
    public void insertUserLog(UserLog userLog) {

    }


    public void insertUser(TestUser user1, TestUser user2) {
        testUserMapper.insert(user2);
        if (user1 != null) {
            testUserMapper.insert(user1);
        }
//                int a = 1/0;

    }
}
