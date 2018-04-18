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

    @Override
    public TestUser findUser(Integer id) {
//        Sku sku = skuService.get(1);
//        System.out.println(sku);
        TestUser testUser = testUserMapper.selectByPrimaryKey(id);
        logger.info("user.name:" + testUser.getName() + "user.address"
                + testUser.getAddress());
        return testUser;
    }

    @Override
    public void insertUser(TestUser user) {
//        TestUser testUser = new TestUser();
//        testUser.setId(null);
//        testUser.setName("小明");
//        testUser.setAge(18);
//        testUser.setAddress("北京");
//        testUser.setTelephone("134XXXXXXXXXX");
        testUserMapper.insert(user);

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
        TestUser user1 = new TestUser(null, "110021", 21, "China", "小一");
        TestUser user2 = new TestUser(null, "110022", 22, "China", "小二");
        TestUser user3 = new TestUser(null, "110023", 23, "China", "小三");
        TestUser user4 = new TestUser(null, "110024", 24, "China", "小四");
        testUserMapper.insert(user1);
        System.exit(0);
        testUserMapper.insert(user2);
//                int a = 1/0;

//        this.insertUser();
        insertUser(user3, user4);


//        try {
//            insertUser(user3, user4);
//        } catch (Exception e) {
//            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        }
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
        testUserMapper.insert(user1);
        if (user1 != null) {
            testUserMapper.insert(user2);
        }
        int a = 1 / 0;

    }
}
