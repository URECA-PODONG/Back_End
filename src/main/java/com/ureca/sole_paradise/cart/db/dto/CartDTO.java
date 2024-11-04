package com.ureca.sole_paradise.cart.db.dto;

import lombok.Data;

@Data
public class CartDTO {
    private Integer cartId;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private String productImage;
    private String productTitle;
    private Integer productLprice;
}
