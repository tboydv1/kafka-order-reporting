package com.bankwithmint.sales.data.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 13/07/2021
 * inside the package - com.bankwithmint.sales.data.dto
 */

@Data
public class ProductDto {


    @NotNull(message = "Product name is required")
    private String name; //should not be null

    private BigDecimal price;

    private Integer quantityInStock;

    private String description;

}
