package com.ecommerce.OrderService.service;

import com.ecommerce.OrderService.entity.Order;
import com.ecommerce.OrderService.exception.OrderNotFoundException;
import com.ecommerce.OrderService.external.serviceCalls.ExternalServiceCall;
import com.ecommerce.OrderService.model.OrderRequest;
import com.ecommerce.OrderService.model.OrderResponse;
import com.ecommerce.OrderService.model.PaymentMode;
import com.ecommerce.OrderService.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
//import org.mockito.Mockito;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private RestTemplate restTemplate; // RestTemplate.class

    @Mock
    ExternalServiceCall ex;

    @InjectMocks
    OrderService orderService= new OrderServiceImpl();

    @DisplayName("Get order details success scenario")
    @Test
    void test_when_getOrderDetails_success(){

        // Mocking
        Order order= getMockOrder();

        // by writing this we are saying that whenever orderRepository.findById(anyLong()) is called return whatever we have defined in order should be returned
        Mockito.when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));

        // by writing this we are saying that whenever getById is called return getMockProductResponse
//        Mockito.when(restTemplate.getForObject("http://PRODUCT-SERVICE/product/getById/"+order.getProductId(), OrderResponse.ProductDetails.class))
//        .thenReturn(getMockProductResponse());

        Mockito.when(ex.getProductDetails(Mockito.anyString(),Mockito.any()))
                .thenReturn(getMockProductResponse());

        // Actual
        OrderResponse orderResponse  =orderService.getOrderDetails(1); // here we are doing actual call to our getOrderDetails but the inside calls i.e. orderRepository.findById call or restTemplate.getForObject won't happen

        // Verification
        // verify that orderRepository.findById happened once
        Mockito.verify(orderRepository, Mockito.times(1)).findById(anyLong());

        Mockito.verify(ex,Mockito.times(1)).getProductDetails(Mockito.anyString(),Mockito.any());

        // Assertion
        assertNotNull(orderResponse);
        assertEquals(order.getId(),orderResponse.getId());
    }

    @DisplayName("Get order details failure scenario")
    @Test
    void test_getOrderDetails_when_Order_Not_found() { // not working

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

        // assertion
        assertEquals("ORDER_ID_NOT_FOUND",exception.getMessage());
        assertEquals(404,exception.getErrorCode());

    }

    @DisplayName("Place order success scenario")
    @Test
    void test_when_addOrder_success(){

        OrderRequest orderRequest= getMockOrderRequest();
        Order order= getMockOrder();

        Mockito.when(orderRepository.save(Mockito.any(Order.class)))
                        .thenReturn(order);


//        Mockito.when(restTemplate.getForObject("http://PRODUCT-SERVICE/product/reduceQuantity/"+ orderRequest.getProductId()+"/?quantity="+orderRequest.getQuantity(), ResponseEntity.class))
//                .thenReturn(new ResponseEntity<Void>(HttpStatus.OK));

        Mockito.when(ex.reduceQuantityOfProduct(Mockito.anyString(),Mockito.any()))
                .thenReturn(new ResponseEntity<Void>(HttpStatus.OK));


        Long orderId= orderService.addOrder(orderRequest);

        // verification

        Mockito.verify(orderRepository,Mockito.times(1)).save(Mockito.any());

        //Mockito.verify(restTemplate,Mockito.times(1)).getForObject("http://PRODUCT-SERVICE/product/reduceQuantity/"+ orderRequest.getProductId()+"/?quantity="+orderRequest.getQuantity(), ResponseEntity.class);

        assertEquals(order.getId(),orderId);

    }

    private OrderResponse.ProductDetails getMockProductResponse() {

        return OrderResponse.ProductDetails.builder()
                .productId(2)
                .productName("phone")
                .price(300)
                .quantity(200)
                .build();
    }

    private Order getMockOrder() {

        return Order.builder()
                .orderStatus("PLACED")
                .orderDate(Instant.now())
                .productId(1)
                .amount(200)
                .quantity(1)
                .id(0)
                .build();
    }

    private OrderRequest getMockOrderRequest(){
        return OrderRequest.builder()
                .productId(1)
                .amount(200)
                .quantity(1)
                .paymentMode(PaymentMode.CASH)
                .build();
    }


}