package com.bankwithmint.sales.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 12/07/2021
 * inside the package - com.bankwithmint.sales.data.models
 */

@Entity
@Data
@NoArgsConstructor
public class SalesOrderProduct {

    @EmbeddedId
    @JsonIgnore
    private OrdersProductKey ordersProductKey;

    @Column(nullable = false)
    private Integer quantity;

    public SalesOrderProduct(SalesOrder salesOrder, Product product, Integer quantity){
        this.ordersProductKey = new OrdersProductKey(salesOrder, product);
        this.quantity = quantity;
    }

    @Transient
    public Product getProduct() {
        return this.ordersProductKey.getProduct();
    }

    @Transient
    public Double getTotalPrice() {
        return getProduct().getPrice() * getQuantity();
    }

}
