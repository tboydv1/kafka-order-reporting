package com.bankwithmint.sales.client.api;

import com.bankwithmint.sales.data.dto.OrderDto;
import com.bankwithmint.sales.data.dto.OrderProductDto;
import com.bankwithmint.sales.data.models.Customer;
import com.bankwithmint.sales.data.models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 13/07/2021
 * inside the package - com.bankwithmint.sales.client.api
 */

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts={"classpath:/db/insert.sql"})
class OrderControllerTest {


    @Autowired
    MockMvc mockMvc;

    Customer customer;
    List<OrderProductDto> orderProducts;

    @BeforeEach
    void setUp() {

        orderProducts =
                List.of(OrderProductDto.builder().productId(23L).quantity(3).build(),
                        OrderProductDto.builder().productId(24L).quantity(2).build(),
                        OrderProductDto.builder().productId(25L).quantity(1).build());

        customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john@mail.com");
        customer.setAddress("999 anonymous street");
        customer.setPhone("0908978684");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createOrderTest() throws Exception {

        OrderDto orderDto = new OrderDto();
        orderDto.setCustomer(customer);
        orderDto.setOrderProducts(orderProducts);


        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(
                post("/v1/api/products/")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(orderDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}