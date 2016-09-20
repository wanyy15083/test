package com.test.test;

import com.gome.o2m.ic.schedule.service.IcScheduleService;
import com.test.service.TestPicture;
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
@ContextConfiguration(locations = { "classpath*:spring/spring-config.xml" })
public class BaseTestCase extends AbstractJUnit4SpringContextTests {

//	@Autowired
//	private TestPicture testPicture;
	@Autowired
	private IcScheduleService icScheduleService;
    
//
//	@Test
//	public void testPicture(){
//		testPicture.createPic();
//	}

	@Test
	public void testIc1(){
		icScheduleService.pushSku("100283186");

	}
}