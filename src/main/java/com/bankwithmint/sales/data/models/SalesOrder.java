package com.bankwithmint.sales.data.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 12/07/2021
 * inside the package - com.bankwithmint.sales.data.models
 */
@Entity()
@Table(name = "sales_order")
@Data
@EqualsAndHashCode(callSuper = true) //Ignoring call to super Object
public class SalesOrder extends RepresentationModel<SalesOrder> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrdersProduct> ordersProducts;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    private Double TotalPrice;

    public SalesOrder(){
        ordersProducts = new ArrayList<>();
    }





}
