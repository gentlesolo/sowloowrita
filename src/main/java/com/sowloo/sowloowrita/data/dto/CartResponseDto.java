package com.sowloo.sowloowrita.data.dto;

import com.sowloo.sowloowrita.data.models.Item;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartResponseDto {

    private List<Item> totalItemsInCart;

    private double totalPrice;


}
