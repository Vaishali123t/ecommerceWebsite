package com.ecommerce.OrderService.service;

import com.ecommerce.OrderService.model.OrderRequest;
import com.ecommerce.OrderService.model.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface OrderService {

    Long addOrder(OrderRequest order);

    OrderResponse getOrderDetails(long orderId);

}
