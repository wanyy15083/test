package com.test.entity;

import org.jeecgframework.poi.handler.inter.IExcelModel;

public class SkuPictureImport extends SkuPicture implements IExcelModel {

	private static final long serialVersionUID = 1L;
	private String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
