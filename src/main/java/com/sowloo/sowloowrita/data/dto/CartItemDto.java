package com.sowloo.sowloowrita.data.dto;

import lombok.Data;

@Data
public class CartItemDto {

    private Long userId;

    private Long productId;

    private int quantity;
}
