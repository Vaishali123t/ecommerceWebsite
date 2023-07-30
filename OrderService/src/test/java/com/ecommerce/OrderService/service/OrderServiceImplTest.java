package com.ecommerce.OrderService.service;

import com.ecommerce.OrderService.entity.Order;
import com.ecommerce.OrderService.exception.OrderNotFoundException;
import com.ecommerce.OrderService.model.OrderResponse;
import com.ecommerce.OrderService.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
//import org.mockito.Mockito;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    OrderService orderService= new OrderServiceImpl();

    @DisplayName("Get order details success scenario")
    @Test
    void test_when_getOrderDetails_success(){

        // Mocking
        Order order= gerMockOrder();

        // by writing this we are saying that whenever orderRepository.findById(anyLong()) is called return whatever we have defined in order
        Mockito.when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));

        // by writing this we are saying that whenever getById is called return getMockProductResponse
        Mockito.when(restTemplate.getForObject("http://PRODUCT-SERVICE/product/getById/"+order.getProductId(), OrderResponse.ProductDetails.class))
        .thenReturn(getMockProductResponse());

        // Actual
        OrderResponse orderResponse  =orderService.getOrderDetails(1); // here we are doing actual call to our getOrderDetails

        // Verification
        // verify that orderRepository.findById happened once
        Mockito.verify(orderRepository, Mockito.times(1)).findById(anyLong());

        Mockito.verify(restTemplate,Mockito.times(1)).getForObject("http://PRODUCT-SERVICE/product/getById/"+order.getProductId(), OrderResponse.ProductDetails.class);

        // Assertion
        assertNotNull(orderResponse);
        assertEquals(order.getId(),orderResponse.getId());


    }

    @DisplayName("Get order details failure scenario")
    @Test
    void test_getOrderDetails_when_Order_Not_found() {

        // Mockito
        Mockito.when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(null));

        // Actual
        OrderResponse orderResponse  =orderService.getOrderDetails(1);

        // assert
        OrderNotFoundException exception= assertThrows(OrderNotFoundException.class,
                ()-> orderService.getOrderDetails(1));

        // verify
        Mockito.verify(orderRepository, Mockito.times(1)).findById(anyLong());

        // assetion
        assertEquals("ORDER_ID_NOT_FOUND",exception.getMessage());
        assertEquals(404,exception.getErrorCode());

    }

    private OrderResponse.ProductDetails getMockProductResponse() {

        return OrderResponse.ProductDetails.builder()
                .productId(2)
                .productName("phone")
                .price(300)
                .quantity(200)
                .build();
    }

    private Order gerMockOrder() {

        return Order.builder()
                .orderStatus("PLACED")
                .orderDate(Instant.now())
                .productId(2)
                .amount(100)
                .quantity(200)
                .id(1)
                .build();
    }


}