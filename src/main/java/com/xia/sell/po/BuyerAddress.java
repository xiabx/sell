package com.xia.sell.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerAddress {
    private String addressId;

    private String openid;

    private String latitude;

    private String longitude;

    private String location;

    private String detail;

    private String buyerName;

    private String buyerPhone;

    //private Integer remove;

    private String createTime;

    private String updateTime;
}