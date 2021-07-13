package com.bankwithmint.sales.service.order;

import com.bankwithmint.sales.data.dto.OrderDto;
import com.bankwithmint.sales.data.dto.OrderProductDto;
import com.bankwithmint.sales.data.models.Order;
import com.bankwithmint.sales.data.models.OrderProduct;
import com.bankwithmint.sales.data.models.Product;
import com.bankwithmint.sales.data.repository.OrderRepository;
import com.bankwithmint.sales.data.repository.ProductRepository;
import com.bankwithmint.sales.service.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


    @Override
    public Order createOrder(OrderDto orderDto) {

        Order order = new Order();
        order.setCustomer(orderDto.getCustomer());
        List<OrderProduct> orderProducts = new ArrayList<>();

        //check product availability
        for(OrderProductDto orderProductDto : orderDto.getOrderProducts()){
            Product product = productRepository.findById(orderProductDto.getProductId()).orElse(null);
            if(product != null){

                orderProducts.add(new OrderProduct(order, product, orderProductDto.getQuantity()));

                Integer newQuantity = Math.abs(product.getQuantityInStock() - orderProductDto.getQuantity());
                product.setQuantityInStock(newQuantity);
                productRepository.save(product);

            }
        }
        order.setOrderProducts(orderProducts);
        Order createdOrder = orderRepository.save(order);
        publishOrderReports(createdOrder);
        return createdOrder;
    }

    private void publishOrderReports(Order orderCreationPayload){
        kafkaProducer.send(topic, orderCreationPayload.toString());
    }


}
