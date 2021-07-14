package com.bankwithmint.sales.service.order;

import com.bankwithmint.sales.client.exceptions.OrderNotCreatedExeption;
import com.bankwithmint.sales.data.dto.OrderDto;
import com.bankwithmint.sales.data.models.SalesOrder;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 12/07/2021
 * inside the package - com.bankwithmint.sales.service.order
 */


public interface OrderService {
    public SalesOrder createOrder(OrderDto order) throws OrderNotCreatedExeption;
}