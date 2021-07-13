package com.bankwithmint.sales.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 12/07/2021
 * inside the package - com.bankwithmint.sales.data.models
 */

@Entity()
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class Product extends RepresentationModel<Product> {

    @Id @Setter @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Setter @Getter
    private String name; //should not be null

    @Column()
    @Setter @Getter
    private BigDecimal price;

    @Getter
    @Setter
    private Integer quantityInStock;

    @Setter @Getter
    private String description;

    @CreationTimestamp
    @Setter @Getter
    private LocalDateTime dateCreated;

    public Product(String name, BigDecimal price, Integer quantityInStock, String description) {
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.description = description;
    }

    public Product(Long id, String name, BigDecimal price, Integer quantityInStock, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.description = description;
    }


}
