package com.bankwithmint.sales.data.dto;

import com.bankwithmint.sales.data.models.Customer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 13/07/2021
 * inside the package - com.bankwithmint.sales.data.dto
 */

@Data
public class OrderDto {

    @NotNull(message = "Customer info is required")
    private Customer customer;

    private List<OrderProductDto> orderProducts;


}
