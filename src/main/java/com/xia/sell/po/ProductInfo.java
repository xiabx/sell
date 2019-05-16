package com.xia.sell.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo {
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer sold;

    private String productDescription;

    private String productIcon;

    private Byte productStatus;

    private String categoryType;

    private String sellerId;
    private Integer remove;
    private Date createTime;

    private Date updateTime;
}