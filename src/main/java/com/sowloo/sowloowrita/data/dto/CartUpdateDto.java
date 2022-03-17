package com.sowloo.sowloowrita.data.dto;


import com.sowloo.sowloowrita.data.models.QuantityOp;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartUpdateDto {

    private Long userId;

    private Long itemId;

    private QuantityOp quantityOp;
}
