package com.xia.sell.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderMaster {
    private String orderId;
    private String buyerOpenid;
    private String buyerName;
    private String buyerLocation;
    private String buyerAddress;
    private String buyerPhone;

    private BigDecimal orderAmount;

    private Byte orderStatus;

    private String sellerId;
    private String note;
    private Integer remove;
    private String createTime;

    private String updateTime;

}