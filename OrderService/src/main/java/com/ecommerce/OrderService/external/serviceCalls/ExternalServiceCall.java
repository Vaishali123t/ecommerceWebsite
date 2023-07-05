package com.ecommerce.OrderService.external.serviceCalls;


import com.ecommerce.OrderService.exception.CustomException;
import com.ecommerce.OrderService.model.OrderResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class ExternalServiceCall {

    // circuitbreaker is not working. fallbackmethod not getting executed
    @CircuitBreaker(name = "product-service-circuit-breaker", fallbackMethod = "fallbackResponse")
    @Retry(name = "product-service-retry")
    public ResponseEntity<Void> reduceQuantityOfProduct(String productServiceUrl,RestTemplate restTemplate){

        log.info("restemplate here "+ restTemplate);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Void> productServiceResponse= restTemplate.exchange(productServiceUrl, HttpMethod.PUT, requestEntity, Void.class);

        return productServiceResponse;
    }

    // circuitbreaker is not working. fallbackmethod not getting executed

    @CircuitBreaker(name = "product-service-circuit-breaker", fallbackMethod = "fallbackResponse1")
    @Retry(name = "product-service")
    public OrderResponse.ProductDetails getProductDetails(String url,RestTemplate restTemplate){

        log.info("restemplate is "+ restTemplate);
        OrderResponse.ProductDetails response=restTemplate.getForObject(url, OrderResponse.ProductDetails.class);

        return response;
    }

    // this fallbackmethod is not getting executed

    public ResponseEntity<Void> fallbackResponse(String url,RestTemplate restTemplate,Exception e){
        log.info("fallback1");
        throw new CustomException("Product service is not available","UNAVAILABLE",500);
    }

    // this fallbackmethod is not getting executed
    public OrderResponse.ProductDetails fallbackResponse1(String url,RestTemplate restTemplate,Exception e){
        log.info("fallback2");
        throw new CustomException("Product service is not available","UNAVAILABLE",500);
    }

}
