package com.test.test;

import com.test.entity.Sku;
import com.test.entity.SkuPicture;
import com.test.service.TestExcelService;
import com.test.service.TestUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by songyigui on 2016/8/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/spring-config.xml" })
public class TestMysqlImportExport {

    @Autowired
    private TestExcelService testExcelService;

    @Test
    public void testMysql() {
        List<SkuPicture> skuPictures = testExcelService.selectSkuPictureList();
        for (SkuPicture skuPicture : skuPictures) {
            System.out.println(skuPicture.getSkuId());
        }
    }

    @Test
    public void testPage(){
        List<Sku> skus = testExcelService.selectSKuListByPage(1, 0);
        System.out.println(skus);
    }


}
