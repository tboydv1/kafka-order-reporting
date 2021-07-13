package com.bankwithmint.sales.service.product;

import com.bankwithmint.sales.client.exceptions.ProductDoesNotExistsException;
import com.bankwithmint.sales.data.dto.ProductDto;
import com.bankwithmint.sales.data.models.Product;
import com.bankwithmint.sales.data.repository.ProductRepository;
import com.bankwithmint.sales.service.mapper.ProductMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.configuration.MockAnnotationProcessor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 13/07/2021
 * inside the package - com.bankwithmint.sales.service.product
 */

class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductMapper productMapper;

    @InjectMocks
    ProductService productService;

    ProductDto productDto = new ProductDto();


    @BeforeEach
    void setUp() {

        productService = new ProductServiceImpl();
        MockitoAnnotations.openMocks(this);

        productDto.setName("Blue Journal");
        productDto.setDescription("This is an ocean blue journal");
        productDto.setPrice(BigDecimal.valueOf(1300));
        productDto.setQuantityInStock(13);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createProductTest(){

        when(productService.createProduct(productDto)).thenReturn(new Product());
        productService.createProduct(productDto);
        verify(productRepository).save(any(Product.class));

    }

    @Test
    void getProductsTest(){

        List<Product> products = new ArrayList<>();
        products.add(new Product(22L, "Hammer",  BigDecimal.valueOf(1400), 24,"this is a Big Hammer"));

        when(productService.getProducts()).thenReturn(products);
        productService.getProducts();

        verify(productRepository, times(1)).findAll();

        assertThat(products.size()).isEqualTo(1);
    }





}