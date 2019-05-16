package com.xia.sell.po;

import java.util.Date;

public class SellerDiscount {
    private String discountId;

    private String sellerId;

    private Integer full;

    private Integer reduce;

    private Date createTime;

    private Date updateTime;

    public SellerDiscount(String discountId, String sellerId, Integer full, Integer reduce, Date createTime, Date updateTime) {
        this.discountId = discountId;
        this.sellerId = sellerId;
        this.full = full;
        this.reduce = reduce;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public SellerDiscount() {
        super();
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId == null ? null : discountId.trim();
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    public Integer getFull() {
        return full;
    }

    public void setFull(Integer full) {
        this.full = full;
    }

    public Integer getReduce() {
        return reduce;
    }

    public void setReduce(Integer reduce) {
        this.reduce = reduce;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}