package com.test.entity;

import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * 商品SKU信息
 */
public class Sku implements Serializable {

	private static final long serialVersionUID = 1L;
	// 商品内码
	@Excel(name = "商品内码")
	private Integer id;
	// SAP品类代码
	@Excel(name = "SAP品类代码", mergeVertical = true)
	private Integer categoryId;
	// 品牌代码
	private Integer brandId;
	// ERP代码
	private String erpCode;
	// 商品编码
	private String skuCode;
	// 条码
	private String barCode;
	// 商品名称
	@Excel(name = "商品名称")
	private String name;
	// 计量单位
	private String unit;
	// 0非经营商品 1自经营商品
	private Integer isGiftGoods;
	// 特殊标志（赠品、红卡、兰卡、会员商品）
	private String specialGoodsFlag;
	// 获取该sku的二级类目 查询价格过期时用
	private String category;
	// 商品类型
	private String goodsType;
	// 状态
	private Integer status;
	// 维护时间
	private String lastUpdate;

	public Sku() {
	}

	public Sku(Integer id, Integer categoryId, Integer brandId, String erpCode, String skuCode, String barCode, String name, String unit, Integer isGiftGoods, String specialGoodsFlag,
			String category, String goodsType, Integer status, String lastUpdate) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.brandId = brandId;
		this.erpCode = erpCode;
		this.skuCode = skuCode;
		this.barCode = barCode;
		this.name = name;
		this.unit = unit;
		this.isGiftGoods = isGiftGoods;
		this.specialGoodsFlag = specialGoodsFlag;
		this.category = category;
		this.goodsType = goodsType;
		this.status = status;
		this.lastUpdate = lastUpdate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getIsGiftGoods() {
		return isGiftGoods;
	}

	public void setIsGiftGoods(Integer isGiftGoods) {
		this.isGiftGoods = isGiftGoods;
	}

	public String getSpecialGoodsFlag() {
		return specialGoodsFlag;
	}

	public void setSpecialGoodsFlag(String specialGoodsFlag) {
		this.specialGoodsFlag = specialGoodsFlag;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "Sku [id=" + id + ", categoryId=" + categoryId + ", brandId=" + brandId + ", erpCode=" + erpCode + ", skuCode=" + skuCode + ", barCode=" + barCode + ", name=" + name + ", unit=" + unit
				+ ", isGiftGoods=" + isGiftGoods + ", specialGoodsFlag=" + specialGoodsFlag + ", category=" + category + ", goodsType=" + goodsType + ", status=" + status + ", lastUpdate="
				+ lastUpdate + "]";
	}

}