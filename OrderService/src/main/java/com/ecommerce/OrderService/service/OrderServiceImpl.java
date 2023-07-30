package com.ecommerce.OrderService.service;

import com.ecommerce.OrderService.entity.Order;
import com.ecommerce.OrderService.exception.OrderNotFoundException;
import com.ecommerce.OrderService.external.serviceCalls.ExternalServiceCall;
import com.ecommerce.OrderService.model.ErrorResponse;
import com.ecommerce.OrderService.model.OrderRequest;
import com.ecommerce.OrderService.model.OrderResponse;
import com.ecommerce.OrderService.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
//    @CircuitBreaker(name="external", fallbackMethod = "fallback")
    public Long addOrder(OrderRequest orderRequest) {

        log.info("Order addition started");
        // ORDER ENTITY -> SAVE DATA WITH STATUS ORDER CREATED
        // IN PRODUCT SERVICE REDUCE THE QUANTITY OF PRODUCT IF CAN'T THROW ERROR
        // CALL PAYMENT SERVICE-> IF SUCCESS -> ORDER PLACED SUCCESSFULLY ELSE ORDER REFUSED
        Order order= Order.builder()
                        .amount(orderRequest.getAmount())
                                .orderStatus("CREATED")
                                        .productId(orderRequest.getProductId())
                                                .quantity(orderRequest.getQuantity())
                                                        .orderDate(Instant.now())
                                                                .build();

        String productServiceUrl= "http://PRODUCT-SERVICE/product/reduceQuantity/"+ orderRequest.getProductId()+"/?quantity="+orderRequest.getQuantity();

        ExternalServiceCall ex= new  ExternalServiceCall();
        ResponseEntity<Void> productServiceResponse= ex.reduceQuantityOfProduct(productServiceUrl,restTemplate);

        if(productServiceResponse.getStatusCode().is2xxSuccessful()){
                orderRepository.save(order);
                log.info("Order placed successfully with order id {}",order.getId());
            }
            else{
                log.info("Order could not be placed ");
            }
//        catch (Exception e){

//            ErrorResponse errorResponse= new ErrorResponse()
//               throw ResponseEntity.internalServerError();
//            return new ResponseEntity<>(new ErrorResponse().builder()
//                    .errorMessage(e.getMessage())
//                    .errorCode(String.valueOf(HttpStatus.NOT_FOUND))
//                    .build());
//            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders().body(e.getResponseBodyAsString());
//            return ResponseEntity<>(e.getResponseBodyAsString(), e.getResponseHeaders(), e.getStatusCode());
        return order.getId();
    }

    @Override
//    @CircuitBreaker(name="external", fallbackMethod = "fallback")
    public OrderResponse getOrderDetails(long orderId) {

        log.info("Getting order details from getOrderDetails");
        Order order= orderRepository.findById(orderId)
                .orElseThrow(()->new OrderNotFoundException("Order could not found with orderId= "+orderId,"ORDER_ID_NOT_FOUND") );

        log.info("order info found");
        // get product id from order
        long productId= order.getProductId();

        // now call product service to get the name of product

        String url= "http://PRODUCT-SERVICE/product/getById/"+productId;
        ExternalServiceCall ex= new ExternalServiceCall();

        OrderResponse.ProductDetails response= ex.getProductDetails(url,restTemplate);

        log.info("Response from product/getById is fine");

        OrderResponse orderResponse= OrderResponse.builder()
                .id(order.getId())
                .quantity(order.getQuantity())
                .amount(order.getAmount())
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .productDetails(response)
                .build();

        return orderResponse;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }
}
