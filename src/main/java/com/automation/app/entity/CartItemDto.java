package com.automation.app.entity;

import lombok.Data;

@Data
public class CartItemDto {

	private Integer productId;  
	private String productName;
	private Double price;
    private Integer quantity;
    
}
