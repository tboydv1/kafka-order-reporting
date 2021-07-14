package com.bankwithmint.sales.data.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 14/07/2021
 * inside the package - com.bankwithmint.sales.data.dto
 */
@Data
public class OrderReportDto {

    private Long orderId;

    private Integer totalProducts;

    private LocalDate dateCreated;

    private Double totalPrice;
}
