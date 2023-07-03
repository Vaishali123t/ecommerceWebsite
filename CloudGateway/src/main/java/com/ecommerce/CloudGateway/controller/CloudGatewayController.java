package com.ecommerce.CloudGateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CloudGatewayController {

    @GetMapping("/orderServiceFallback")
    public String orderServiceFallbackController(){
        return "ORDER SERVICE IS DOWN";
    }

    @GetMapping("/productServiceFallback")
    public String productServiceFallbackController(){
        return "PRODUCT SERVICE IS DOWN";
    }

}
