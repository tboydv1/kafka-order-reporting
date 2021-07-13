package com.bankwithmint.sales.data.repository;

import com.bankwithmint.sales.data.models.Customer;
import com.bankwithmint.sales.data.models.Order;
import com.bankwithmint.sales.data.models.OrderProduct;
import com.bankwithmint.sales.data.models.Product;
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
class OrderRepositoryTest {

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

        Order order = new Order();
        order.setCustomer(customer);

        List<OrderProduct> orderProducts =initializeProducts(order);
        //add product to order
        order.setOrderProducts(orderProducts);

        log.info("Order before save --> {}", order);
        orderRepository.save(order);
        assertThat(order.getId()).isNotNull();

        log.info("Order after save --> {}", order);
        Order createdOrder = orderRepository.findById(order.getId()).orElse(null);
        assertThat(createdOrder).isNotNull();
        assertThat(createdOrder.getOrderProducts().size()).isEqualTo(5);
        assertThat(createdOrder.getCustomer()).isNotNull();
    }

    private List<OrderProduct> initializeProducts(Order order){
        List<OrderProduct> orderProducts = new ArrayList<>();

        for(Product product : existingProducts ){
            int quantity = 1 + random.nextInt(10);
            if(checkAvailability(product, quantity)) {
                orderProducts.add(new OrderProduct(order, product, quantity));
                product.setQuantityInStock(product.getQuantityInStock() - quantity);
            }
        }
        return orderProducts;
    }

    private boolean checkAvailability(Product product, int quantity){
        return product.getQuantityInStock() >= quantity;
    }










}