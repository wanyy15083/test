package com.test.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 商品二维码查询
 * 
 * @author jiaochunxiao 
 * 2016年3月15日 下午4:23:39
 */
public class GoodsTwoCodeReq implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<String> storeCode;
	private Integer skuId;
	private List<String> saleOrgCode;//销售组织代码
	private String brandCode;  
	private String brandName;  
	private String catorageCode;
	private String catorageName;
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getCatorageCode() {
		return catorageCode;
	}
	public void setCatorageCode(String catorageCode) {
		this.catorageCode = catorageCode;
	}
	public List<String> getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(List<String> storeCode) {
		this.storeCode = storeCode;
	}
	public List<String> getSaleOrgCode() {
		return saleOrgCode;
	}
	public void setSaleOrgCode(List<String> saleOrgCode) {
		this.saleOrgCode = saleOrgCode;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getCatorageName() {
		return catorageName;
	}
	public void setCatorageName(String catorageName) {
		this.catorageName = catorageName;
	}
	

}
