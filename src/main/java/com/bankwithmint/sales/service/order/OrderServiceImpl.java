package com.bankwithmint.sales.service.order;

import com.bankwithmint.sales.data.dto.OrderDto;
import com.bankwithmint.sales.data.dto.OrderProductDto;
import com.bankwithmint.sales.data.models.SalesOrder;
import com.bankwithmint.sales.data.models.OrdersProduct;
import com.bankwithmint.sales.data.models.Product;
import com.bankwithmint.sales.data.repository.OrderRepository;
import com.bankwithmint.sales.data.repository.ProductRepository;
//import com.bankwithmint.sales.service.kafka.KafkaProducer;
import com.bankwithmint.sales.service.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 12/07/2021
 * inside the package - com.bankwithmint.sales.service.order
 */

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    KafkaProducer kafkaProducer;

    @Value("${topic}")
    private String topic;


    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock writeLock = lock.writeLock();
    private final Lock readLock = lock.readLock();


    @Override
    public SalesOrder createOrder(OrderDto orderDto) {

        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setCustomer(orderDto.getCustomer());
        List<OrdersProduct> ordersProducts = new ArrayList<>();


        //check product availability
        for(OrderProductDto orderProductDto : orderDto.getOrderProducts()){
            Product product = productRepository.findById(orderProductDto.getProductId()).orElse(null);
            if(product != null && product.getQuantityInStock() >= orderProductDto.getQuantity()){

                ordersProducts.add(new OrdersProduct(salesOrder, product, orderProductDto.getQuantity()));

                Integer newQuantity = Math.abs(product.getQuantityInStock() - orderProductDto.getQuantity());
                product.setQuantityInStock(newQuantity);
                productRepository.save(product);

            }
        }

        salesOrder.setOrdersProducts(ordersProducts);
        SalesOrder createdSalesOrder = orderRepository.save(salesOrder);
        publishOrderReports(createdSalesOrder);
        return createdSalesOrder;
    }

       private void publishOrderReports(SalesOrder salesOrder){
        kafkaProducer.send(topic, salesOrder.toString());
    }


}
