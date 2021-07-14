package com.bankwithmint.sales.service.order;

import com.bankwithmint.sales.client.exceptions.OrderNotCreatedExeption;
import com.bankwithmint.sales.data.dto.OrderDto;
import com.bankwithmint.sales.data.dto.OrderProductDto;
import com.bankwithmint.sales.data.dto.OrderReportDto;
import com.bankwithmint.sales.data.models.SalesOrder;
import com.bankwithmint.sales.data.models.SalesOrderProduct;
import com.bankwithmint.sales.data.models.Product;
import com.bankwithmint.sales.data.repository.OrderRepository;
import com.bankwithmint.sales.service.kafka.KafkaProducer;
import com.bankwithmint.sales.service.product.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    @Autowired
    KafkaProducer kafkaProducer;

    @Value("${topic}")
    private String topic;


    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock writeLock = lock.writeLock();
    private final Lock readLock = lock.readLock();


    @Override
    public SalesOrder createOrder(OrderDto orderDto) throws OrderNotCreatedExeption {

        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setCustomer(orderDto.getCustomer());
        List<SalesOrderProduct> salesOrderProducts;

        try {
            writeLock.lock(); //to prevent concurrent modification

            //initializes orderProduct
            salesOrderProducts = initializeProductOrders(salesOrder, orderDto.getOrderProducts());

            if (!(salesOrderProducts.size() > 0)){
                throw new OrderNotCreatedExeption("Order is not created --> please make sure product exists");
            }
                salesOrder.setSalesOrderProducts(salesOrderProducts);
                salesOrder = orderRepository.save(salesOrder);


        }finally {
            writeLock.unlock();
        }

        publishOrderReports(salesOrder);

        return salesOrder;
    }

    private void publishOrderReports(SalesOrder salesOrder) {

        ObjectMapper objectMapper = new ObjectMapper();

        OrderReportDto orderReportDto = new OrderReportDto();
        orderReportDto.setOrderId(salesOrder.getId());
        orderReportDto.setDateCreated(salesOrder.getDateCreated());
        orderReportDto.setTotalPrice(salesOrder.getTotalOrderPrice());

        try {
            kafkaProducer.send(topic, objectMapper.writeValueAsString(orderReportDto.toString()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    private boolean productIsAvailable(Product product, Integer quantity) {
        return product != null && product.getQuantityInStock() >= quantity;
    }

    private List<SalesOrderProduct> initializeProductOrders(SalesOrder salesOrder, List<OrderProductDto> orderDto) {
        List<SalesOrderProduct> products = new ArrayList<>();


            for (OrderProductDto orderProductDto : orderDto) {
                Product product = productService.findById(orderProductDto.getProductId());
                if (productIsAvailable(product, orderProductDto.getQuantity())) {
                    products.add(new SalesOrderProduct(salesOrder, product, orderProductDto.getQuantity()));
                    productService.updateQuantity(product, orderProductDto.getQuantity());
                }
            }

        return products;
    }

}
