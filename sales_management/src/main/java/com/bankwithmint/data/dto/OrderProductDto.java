package com.bankwithmint.data.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 13/07/2021
 * inside the package - com.bankwithmint.sales.data.dto
 */

@Data
@Builder
public class OrderProductDto {


    @NotNull(message = "Product Id cannot be null")
    private Long productId;

    @NotNull(message = "Quantity must be provided for product")
    private Integer quantity;

}
