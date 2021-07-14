package com.bankwithmint.sales.client.api;

import com.bankwithmint.sales.data.dto.OrderDto;
import com.bankwithmint.sales.data.models.SalesOrder;
import com.bankwithmint.sales.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 13/07/2021
 * inside the package - com.bankwithmint.sales.client.api
 */

@RestController
@RequestMapping("/v1/api/orders")
@Slf4j
public class OrderController {



    @Autowired
    OrderService orderService;

    @PostMapping({"", "/"})
    public ResponseEntity<?> save(@Valid @RequestBody OrderDto orderDto)
    {
        SalesOrder salesOrder = orderService.createOrder(orderDto);
        return ResponseEntity.ok().body(salesOrder);
    }

}
