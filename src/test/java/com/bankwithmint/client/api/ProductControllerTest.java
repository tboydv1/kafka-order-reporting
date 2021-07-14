package com.bankwithmint.client.api;

import com.bankwithmint.data.models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getProductsTest() throws Exception {

        mockMvc.perform(get("/v1/api/products/"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void createNewProductTest() throws Exception {

        Product product = new Product("Nails",  1400.00, 24,"this is a Shoe sized nails");

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(
                post("/v1/api/products/")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(product)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void getProducts() {
    }

    @Test
    void save() {
    }

    @Test
    void findById() {
    }

    @Test
    void updateEmployee() {
    }

//    @Test
//    void findEmployeeByIdTest() throws Exception {
//
//        mockMvc.perform(get("/employee/12"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }




}