package com.bankwithmint.data.repository;

import com.bankwithmint.data.models.SalesOrderProduct;
import com.bankwithmint.data.models.Customer;
import com.bankwithmint.data.models.SalesOrder;
import com.bankwithmint.data.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 12/07/2021
 * inside the package - com.bankwithmint.sales.data.repository
 */

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:/db/insert.sql"})
@Transactional
@Rollback(value = false)
class SalesOrderRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;
    Customer customer;

    List<Product> existingProducts;

    Random random;


    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john@mail.com");
        customer.setAddress("999 anonymous street");
        customer.setPhone("0908978684");

        existingProducts = productRepository.findAll();
        assertThat(existingProducts.size()).isEqualTo(5);

        random = new Random();
    }

    @Test
    void WhenOrderIsCreated_thenPersistToDBTest(){

        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setCustomer(customer);

        List<SalesOrderProduct> salesOrderProducts =initializeProducts(salesOrder);
        //add product to order
        salesOrder.setSalesOrderProducts(salesOrderProducts);

        log.info("Order before save --> {}", salesOrder);
        orderRepository.save(salesOrder);
        assertThat(salesOrder.getId()).isNotNull();

        log.info("Order after save --> {}", salesOrder);
        SalesOrder createdSalesOrder = orderRepository.findById(salesOrder.getId()).orElse(null);
        assertThat(createdSalesOrder).isNotNull();
        assertThat(createdSalesOrder.getSalesOrderProducts().size()).isEqualTo(5);
        assertThat(createdSalesOrder.getCustomer()).isNotNull();
    }

    private List<SalesOrderProduct> initializeProducts(SalesOrder salesOrder){
        List<SalesOrderProduct> salesOrderProducts = new ArrayList<>();

        for(Product product : existingProducts ){
            int quantity = 1 + random.nextInt(10);
            if(checkAvailability(product, quantity)) {
                salesOrderProducts.add(new SalesOrderProduct(salesOrder, product, quantity));
                product.setQuantityInStock(product.getQuantityInStock() - quantity);
            }
        }
        return salesOrderProducts;
    }

    private boolean checkAvailability(Product product, int quantity){
        return product.getQuantityInStock() >= quantity;
    }










}