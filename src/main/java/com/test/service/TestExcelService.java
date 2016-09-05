package com.test.service;

import com.test.entity.Sku;
import com.test.entity.SkuPicture;

import java.util.List;

public interface TestExcelService {

	public List<Sku> selectSkuList();

	public List<SkuPicture> selectSkuPictureList();

	public void excelImport(List<SkuPicture> list);

	public int selectSkuPictureOne(SkuPicture skuPicture);

	public List<Sku> selectSKuListByPage(int pageNum,int pageSize);
}
