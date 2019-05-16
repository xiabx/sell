package com.xia.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerProductCategoryVO {
	@JsonProperty("name")
	private String categoryName;
	@JsonProperty("type")
	private String categoryType;

	private List foods;
}
