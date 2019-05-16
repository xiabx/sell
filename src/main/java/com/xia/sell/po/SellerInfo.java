package com.xia.sell.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerInfo {
    private String id;
    private String username;
    private String password;
    private String shopName;
    private String longitude;
    private String latitude;
    private Integer shopSold;
    private String shopIcon;
    private Integer beginFee;
    private Integer distributionFee;
    private Date createTime;
    private Date updateTime;
}