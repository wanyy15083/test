package com.test.test;

/**
 * Created by songyigui on 2016/9/2.
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gome.o2m.Response;
import com.gome.o2m.domain.SearchResult;
import com.gome.o2m.ic.category.service.SkuGmzxPictureReadService;
import com.google.gson.Gson;
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
@ContextConfiguration(locations = {"classpath:spring/spring-config.xml"})
public class TestIc extends AbstractJUnit4SpringContextTests {

    @Autowired
    private TestUserService testUserService;
    @Autowired
    private SkuGmzxPictureReadService skuGmzxPictureReadService;

    @Test
    public void testPic() throws Exception {
        System.out.println("..........................");
        testUserService.findUser();
        Response<SearchResult> list = skuGmzxPictureReadService.list(null, 1, 10);
        System.out.println(new ObjectMapper().writeValueAsString(list.getResult()));
        System.out.println("..........................");
    }

}