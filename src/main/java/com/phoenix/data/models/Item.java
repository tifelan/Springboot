package com.phoenix.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Slf4j
@Builder
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne()
    private Product product;

    private Integer quantityAddedToCart;

    public void setQuantityAddedToCart(Integer quantityAddedToCart) {
        if(product.getQuantity() >= quantityAddedToCart)
            this.quantityAddedToCart = quantityAddedToCart;
        else
            this.quantityAddedToCart = 0;
    }
}
