package com.bankwithmint.reporting.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 14/07/2021
 * inside the package - com.bankwithmint.reporting.data
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private Integer totalOrder;
    private Double totalAmount;

}
