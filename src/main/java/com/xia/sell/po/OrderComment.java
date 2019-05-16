package com.xia.sell.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderComment {

    private String commentId;

    private String sellerId;
    @JsonIgnore
    private String buyerOpenid;
    private String buyerName;
    private String buyerIcon;

    private String content;

    private Integer star;

    private String createTime;

    private String updateTime;

}