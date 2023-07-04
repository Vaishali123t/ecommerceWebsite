package com.ecommerce.OrderService.external.serviceCalls;


import com.ecommerce.OrderService.model.OrderResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class ExternalServiceCall {

//    @Autowired
//    private RestTemplate restTemplate;
    public ResponseEntity<Void> reduceQuantityOfProduct(String productServiceUrl,RestTemplate restTemplate){

        log.info("restemplate here "+ restTemplate);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Void> productServiceResponse= restTemplate.exchange(productServiceUrl, HttpMethod.PUT, requestEntity, Void.class);

        return productServiceResponse;
    }


    public OrderResponse.ProductDetails getProductDetails(String url,RestTemplate restTemplate){

        log.info("restemplate is "+ restTemplate);
        OrderResponse.ProductDetails response=restTemplate.getForObject(url, OrderResponse.ProductDetails.class);


        return response;
    }

}
