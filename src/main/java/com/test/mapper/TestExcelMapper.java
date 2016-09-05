package com.test.mapper;

import com.test.entity.Sku;
import com.test.entity.SkuPicture;

import java.util.List;

public interface TestExcelMapper {

	public List<Sku> selectSkuList();

	public List<SkuPicture> selectSkuPictureList();

	public void insertSkuPicture(SkuPicture skuPicture);
	
	public int selectSkuPictureOne(SkuPicture skuPicture);
}
