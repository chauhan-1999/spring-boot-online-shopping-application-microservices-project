package com.chauhan.microservices.order_service.service;

import com.chauhan.microservices.order_service.dto.OrderRequest;
import com.chauhan.microservices.order_service.entity.Order;
import com.chauhan.microservices.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest) {

        //map OrderRequest to Order obj && save order to OrderRepository
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());//to Create unique order
        order.setPrice(orderRequest.price().multiply(BigDecimal.valueOf(orderRequest.quantity())));
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());

        orderRepository.save(order);
    }

}
