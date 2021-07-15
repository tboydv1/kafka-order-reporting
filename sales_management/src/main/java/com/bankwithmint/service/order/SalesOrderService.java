package com.bankwithmint.service.order;

import com.bankwithmint.client.exceptions.OrderNotCreatedExeption;
import com.bankwithmint.data.dto.OrderDto;
import com.bankwithmint.data.models.SalesOrder;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 12/07/2021
 * inside the package - com.bankwithmint.sales.service.order
 */


public interface SalesOrderService {
    public SalesOrder createOrder(OrderDto order) throws OrderNotCreatedExeption;
}