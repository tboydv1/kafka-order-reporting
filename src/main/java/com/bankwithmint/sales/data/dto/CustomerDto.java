package com.bankwithmint.sales.data.dto;

import lombok.Data;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 13/07/2021
 * inside the package - com.bankwithmint.sales.data.dto
 */

@Data
public class CustomerDto {

    private String name;
    private String email;
    private String phone;
    private String address;

}
