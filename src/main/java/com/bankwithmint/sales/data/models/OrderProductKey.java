package com.bankwithmint.sales.data.models;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 12/07/2021
 * inside the package - com.bankwithmint.sales.data.models
 */

@Embeddable
@NoArgsConstructor
public class OrderProductKey implements Serializable {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Getter
    private Order order;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Getter
    private Product product;

    public OrderProductKey(Order order, Product product){
        this.order = order;
        this.product = product;
    }


}
