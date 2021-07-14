package com.bankwithmint.service.order;

import com.bankwithmint.reporting.data.ReportData;
import com.bankwithmint.client.exceptions.OrderNotCreatedExeption;
import com.bankwithmint.data.dto.OrderDto;
import com.bankwithmint.data.dto.OrderProductDto;
import com.bankwithmint.data.models.SalesOrder;
import com.bankwithmint.data.models.SalesOrderProduct;
import com.bankwithmint.data.models.Product;
import com.bankwithmint.data.repository.OrderRepository;
import com.bankwithmint.service.kafka.KafkaProducer;
import com.bankwithmint.service.product.ProductService;
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

        ReportData reportData = new ReportData();
        reportData.setOrderId(salesOrder.getId());
        reportData.setDateCreated(salesOrder.getDateCreated());
        reportData.setTotalPrice(salesOrder.getTotalOrderPrice());
        reportData.setTotalProducts(salesOrder.getSalesOrderProducts().size());

        try {
            kafkaProducer.send(topic, reportData);
        }catch (Exception e){
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
