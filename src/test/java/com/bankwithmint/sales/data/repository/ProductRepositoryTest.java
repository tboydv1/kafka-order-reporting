package com.bankwithmint.sales.data.repository;

import com.bankwithmint.sales.data.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 12/07/2021
 * inside the package - com.bankwithmint.sales.data.repository
 */

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:/db/insert.sql"})
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenSaveMethodIsCalled_thenSaveProductToDb_AndGenerateProductId(){

        Product product = new Product();
        product.setName("Fire Extinguisher");
        product.setDescription("Large fire extinguisher");
        product.setPrice(500.00);
        product.setQuantityInStock(15);

        log.info("Product object before saving to DB --> {}", product);
        productRepository.save(product);
        assertThat(product.getId()).isNotNull();

        Long generatedProductId = product.getId();

        Product savedProduct = productRepository.findById(generatedProductId).orElse(null);
        assertThat(savedProduct).isNotNull();

        log.info("Product after retrieving from db --> {}", savedProduct);

    }

    @Test
    void whenFindAllMethodIsCalled_thenReturnAllRecordFromDB(){
        List<Product> existingProducts = productRepository.findAll();
        assertThat(existingProducts).isNotNull();
        assertThat(existingProducts.size()).isEqualTo(5);
    }

    @Test
    void whenExistingProductPriceIsUpdated_thenUpdateExistingDatabaseRecord(){
        Product existingProduct = productRepository.findById(22L).orElse(null);
        assertThat(existingProduct).isNotNull();
        assertThat(existingProduct.getName()).isEqualTo("Hammer");
        assertEquals(Math.abs(1400), Math.abs(existingProduct.getPrice().doubleValue()));
        assertThat(existingProduct.getQuantityInStock()).isEqualTo(24);

        //update Name
        existingProduct.setName("American Large Hammer");
        existingProduct.setPrice(400.00);

        productRepository.save(existingProduct);

        Product updatedProduct = productRepository.findById(existingProduct.getId()).orElse(null);
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getName()).isEqualTo("American Large Hammer");
        assertEquals(Math.abs(400), Math.abs(updatedProduct.getPrice().doubleValue()));
    }



}