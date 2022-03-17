package com.sowloo.sowloowrita.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private Product product;

    private Integer quantityAddedCart;

    public Item(Product product, Integer quantityAddedCart){
        if (quantityAddedCart <= product.getQuantity()){
            this.quantityAddedCart = quantityAddedCart;
        }
        this.product = product;
    }

    public void setQuantityAddedCart(Integer quantityAddedCart){
        if (product.getQuantity() >= quantityAddedCart) {
            this.quantityAddedCart = quantityAddedCart;
        }else this.quantityAddedCart = 0;
    }
}
