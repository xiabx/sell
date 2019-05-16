package com.xia.sell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoDTO {
	private String productId;
	@NotBlank(message = "商品名不能为空")
	private String productName;
	@NotNull(message = "商品价格不能为空")
	private BigDecimal productPrice;
	@NotBlank(message = "商品描述不能为空")
	private String productDescription;
	//@NotBlank(message = "商品图片不能为空")
	private MultipartFile productIcon;

	private String productIcon2;

	@NotNull(message = "商品状态不能为空")
	private Integer productStatus;
	@NotNull(message = "商品类别不能为空")
	private String categoryType;
}

