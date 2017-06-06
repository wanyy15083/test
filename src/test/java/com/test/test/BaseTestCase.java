package com.test.test;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

/**
 * 测试基类（无事务回滚）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring-config.xml"})
public class BaseTestCase extends AbstractJUnit4SpringContextTests {

    //	@Autowired
//	private TestPicture testPicture;
    @Autowired
    private AsyncTask asyncTask;

//
//	@Test
//	public void testPicture(){
//		testPicture.createPic();
//	}



    @Test
    public void testAsync() {
        try {
            File temp = new File("1.txt");
            String result = FileUtils.readFileToString(temp, "UTF-8");
            asyncTask.deleteTemp(temp);
            System.out.println(result);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

