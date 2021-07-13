package com.bankwithmint.sales.service.order;

import com.bankwithmint.sales.data.models.Order;
import com.bankwithmint.sales.data.models.OrderProduct;

import java.util.List;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 12/07/2021
 * inside the package - com.bankwithmint.sales.service.order
 */


public interface OrderService {
    public Order createOrder(Order order);
}