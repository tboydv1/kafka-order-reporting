package com.bankwithmint.sales.service.order;

import com.bankwithmint.sales.data.dto.OrderDto;
import com.bankwithmint.sales.data.dto.OrderProductDto;
import com.bankwithmint.sales.data.models.Customer;
import com.bankwithmint.sales.data.models.SalesOrder;
import com.bankwithmint.sales.data.models.OrdersProduct;
import com.bankwithmint.sales.data.models.Product;
import com.bankwithmint.sales.data.repository.OrderRepository;
import com.bankwithmint.sales.data.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 13/07/2021
 * inside the package - com.bankwithmint.sales.service.order
 */

@SpringBootTest
@Sql(scripts = {"classpath:/db/insert.sql"})
@Transactional
@Rollback(value = false)
class SalesOrderServiceImplTest {

    OrderDto orderDto;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    Customer customer;

    @BeforeEach
    void setUp() {

        orderDto = new OrderDto();

        //Creating 3 order products
        List<OrderProductDto> orderProducts =
                List.of(OrderProductDto.builder().productId(23L).quantity(3).build(),
                        OrderProductDto.builder().productId(24L).quantity(2).build(),
                        OrderProductDto.builder().productId(25L).quantity(1).build());

        orderDto.setOrderProducts(orderProducts);

        customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john@mail.com");
        customer.setAddress("999 anonymous street");
        customer.setPhone("0908978684");

        orderDto.setCustomer(customer);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenOrderIsSent_thenValidate_andCreateOrder(){

        orderDto = new OrderDto();

        assertThat(orderDto).isNotNull();
        SalesOrder salesOrder = new SalesOrder();
        List<OrdersProduct> ordersProducts = new ArrayList<>();

        for(OrderProductDto orderProductDto : orderDto.getOrderProducts()){
            Product product = productRepository.findById(orderProductDto.getProductId()).orElse(null);
            if(product != null){
                ordersProducts.add(new OrdersProduct(salesOrder, product, orderProductDto.getQuantity()));

                Integer newQuantity = Math.abs(product.getQuantityInStock() - orderProductDto.getQuantity());
                product.setQuantityInStock(newQuantity);
                productRepository.save(product);

                assertThat(product.getQuantityInStock()).isEqualTo(newQuantity);
            }
        }
        assertThat(ordersProducts.size()).isEqualTo(3);
        salesOrder.setOrdersProducts(ordersProducts);
        salesOrder.setCustomer(orderDto.getCustomer());
    }


    @Test
    void whenOrderIsSent_AndProductIsUnavailable_thenDoNoCreateOrder(){

        OrderDto orderDto = new OrderDto();

        List<OrderProductDto> orderProductsDto =
                List.of(OrderProductDto.builder().productId(231L).quantity(3).build(),
                        OrderProductDto.builder().productId(242L).quantity(2).build(),
                        OrderProductDto.builder().productId(25L).quantity(1).build());


        orderDto.setOrderProducts(orderProductsDto);


        SalesOrder salesOrder = new SalesOrder();
        List<OrdersProduct> ordersProducts = new ArrayList<>();

        for(OrderProductDto orderProductDto : orderDto.getOrderProducts()){
            Product product = productRepository.findById(orderProductDto.getProductId()).orElse(null);
            if(product != null){
                ordersProducts.add(new OrdersProduct(salesOrder, product, orderProductDto.getQuantity()));

                Integer newQuantity = Math.abs(product.getQuantityInStock() - orderProductDto.getQuantity());
                product.setQuantityInStock(newQuantity);
                productRepository.save(product);

                assertThat(product.getQuantityInStock()).isEqualTo(newQuantity);
            }
        }
        assertThat(ordersProducts.size()).isEqualTo(1);
        salesOrder.setOrdersProducts(ordersProducts);
        salesOrder.setCustomer(customer);
    }

    @Test
    void createOrderUsingLambdasTest(){
        OrderDto orderDto = new OrderDto();

        List<OrderProductDto> orderProductsDto =
                List.of(OrderProductDto.builder().productId(231L).quantity(3).build(),
                        OrderProductDto.builder().productId(242L).quantity(2).build(),
                        OrderProductDto.builder().productId(25L).quantity(1).build());


        orderDto.setOrderProducts(orderProductsDto);

        SalesOrder salesOrder = new SalesOrder();
        List<OrdersProduct> ordersProducts = new ArrayList<>();

        orderDto.getOrderProducts().forEach(orderProductDto -> {
            Product product = productRepository.findById(orderProductDto.getProductId()).orElse(null);
            if (product != null) {
                ordersProducts.add(new OrdersProduct(salesOrder, product, orderProductDto.getQuantity()));

                Integer newQuantity = Math.abs(product.getQuantityInStock() - orderProductDto.getQuantity());
                product.setQuantityInStock(newQuantity);
                productRepository.save(product);

                assertThat(product.getQuantityInStock()).isEqualTo(newQuantity);
            }
        });


        Map<Object, List<OrderProductDto>> result = orderDto.getOrderProducts().stream()
                .collect(Collectors.groupingBy(orderProductDto -> productRepository.findById(orderProductDto.getProductId())));

        result.forEach(
                (department, employeesInDepartment) ->
                {
                    System.out.println(department);
                    employeesInDepartment.forEach(
                            employee -> System.out.printf("%s%n", employee));
                }
        );


        assertThat(ordersProducts.size()).isEqualTo(3);
        salesOrder.setOrdersProducts(ordersProducts);
        salesOrder.setCustomer(orderDto.getCustomer());
    }




}