package com.ecommerce.OrderService.controller;

import com.ecommerce.OrderService.model.OrderRequest;
import com.ecommerce.OrderService.model.OrderResponse;
import com.ecommerce.OrderService.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Long> addOrder(@RequestBody OrderRequest order){
        Long orderId= orderService.addOrder(order);
        ResponseEntity<Long> orderIdRes= new ResponseEntity<>(orderId, HttpStatus.CREATED);
        return orderIdRes;
    }

    @GetMapping("/getOrderDetails/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId){

        log.info("get order details hit");

        OrderResponse orderResponse= orderService.getOrderDetails(orderId);

        log.info("get order details done");

        return new ResponseEntity<>(orderResponse,HttpStatus.OK);
    }

}
