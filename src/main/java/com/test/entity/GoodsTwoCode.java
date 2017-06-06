package com.test.entity;

import java.io.Serializable;

/**
 * 商品二维码
 *
 * @author jiaochunxiao
 *         2016年3月15日 下午4:23:39
 */
public class GoodsTwoCode implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 门店代码
     */
    private String storeCode;

    /**
     * 门店名称
     */
    private String  storeName;
    /**
     * 商品内码
     */
    private Integer skuId;

    /**
     * 商品名称
     */
    private String  skuName;
    /**
     * 渠道代码
     */
    private String  channelCode;
    /**
     * 商品二维码内容
     */
    private String  twoCodeMess;
    /**
     * 图片高度
     */
    private Integer height;
    /**
     * 图片宽度
     */
    private Integer width;
    /**
     * 图片URL
     */
    private String  url;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 最后维护人id
     */
    private Integer handlerId;
    /**
     * 最后维护人名称
     */
    private String  handlerName;
    /**
     * 最后维护时间
     */
    private String  handelTime;
    /**
     * 锁定线程id
     */
    private long    lockThreadId;

    /**
     * 图片上传时间
     */
    private String uploadPicTime;

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getTwoCodeMess() {
        return twoCodeMess;
    }

    public void setTwoCodeMess(String twoCodeMess) {
        this.twoCodeMess = twoCodeMess;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHandelTime() {
        return handelTime;
    }

    public void setHandelTime(String handelTime) {
        this.handelTime = handelTime;
    }

    public Integer getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Integer handlerId) {
        this.handlerId = handlerId;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public long getLockThreadId() {
        return lockThreadId;
    }

    public void setLockThreadId(long lockThreadId) {
        this.lockThreadId = lockThreadId;
    }

    public String getUploadPicTime() {
        return uploadPicTime;
    }

    public void setUploadPicTime(String uploadPicTime) {
        this.uploadPicTime = uploadPicTime;
    }

}
