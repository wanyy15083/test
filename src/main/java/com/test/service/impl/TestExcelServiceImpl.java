package com.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.test.entity.Sku;
import com.test.entity.SkuPicture;
import com.test.mapper.TestExcelMapper;
import com.test.service.TestExcelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("testExcelService")
public class TestExcelServiceImpl implements TestExcelService {

    private final static Logger log = LogManager.getLogger(TestExcelServiceImpl.class);

    @Autowired
    private TestExcelMapper testExcelMapper;

    @Override
    public List<Sku> selectSkuList() {
        return testExcelMapper.selectSkuList();
    }

    @Override
    public List<SkuPicture> selectSkuPictureList() {
        return testExcelMapper.selectSkuPictureList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excelImport(List<SkuPicture> list) {
        for (SkuPicture skuPicture : list) {
            testExcelMapper.insertSkuPicture(skuPicture);
        }
    }

    @Override
    public int selectSkuPictureOne(SkuPicture skuPicture) {
        return testExcelMapper.selectSkuPictureOne(skuPicture);
    }

    @Override
    public List<Sku> selectSKuListByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return testExcelMapper.selectSkuList();
    }

}
