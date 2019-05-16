package com.xia.sell.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {
    private Integer categoryId;

    private String categoryName;


    private String categoryType;
    private String sellerId;
    private Integer remove;
    private String createTime;

    private String updateTime;


}