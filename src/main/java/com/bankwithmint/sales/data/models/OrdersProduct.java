package com.bankwithmint.sales.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 12/07/2021
 * inside the package - com.bankwithmint.sales.data.models
 */

@Entity
@Data
@NoArgsConstructor
public class OrdersProduct {

    @EmbeddedId
    @JsonIgnore
    private OrdersProductKey ordersProductKey;

    @Column(nullable = false)
    private Double productPrice;

    @Column(nullable = false)
    private Integer quantity;

    public OrdersProduct(SalesOrder salesOrder, Product product, Integer quantity){
        this.ordersProductKey = new OrdersProductKey(salesOrder, product);
        this.productPrice = product.getPrice().doubleValue() * quantity;
        this.quantity = quantity;
    }
}
