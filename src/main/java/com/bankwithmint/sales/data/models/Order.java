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
@Table(name = "orders")
@Data
@EqualsAndHashCode(callSuper = true) //Ignoring call to super Object
public class Order extends RepresentationModel<Order> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    public Order(){
        orderProducts = new ArrayList<>();
    }





}
