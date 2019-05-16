package com.xia.sell.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 买家端查看订单详情vo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerOrderDetailVO {
	private String orderId;
	private String buyerName;
	private String buyerLocation;
	private String buyerAddress;
	private String buyerPhone;
	private BigDecimal orderAmount;
	//若订单为未支付，剩余支付时间
	private String minute;
	private String second;
	private String orderStatus;
	private Integer orderStatusCode;
	private String shopName;
	private Integer reduce;
	private Integer distributionFee;
	private String note;
	private String createTime;
	private List<cartItem> cart;

	public class cartItem{
		private String productName;
		private BigDecimal productPrice;
		private String productIcon;
		private Integer productQuantity;

		public cartItem() {
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public BigDecimal getProductPrice() {
			return productPrice;
		}

		public void setProductPrice(BigDecimal productPrice) {
			this.productPrice = productPrice;
		}

		public String getProductIcon() {
			return productIcon;
		}

		public void setProductIcon(String productIcon) {
			this.productIcon = productIcon;
		}

		public Integer getProductQuantity() {
			return productQuantity;
		}

		public void setProductQuantity(Integer productQuantity) {
			this.productQuantity = productQuantity;
		}
	}
}
