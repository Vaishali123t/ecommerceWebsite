package com.ecommerce.OrderService.controller;

import com.ecommerce.OrderService.OrderServiceConfig;
import com.ecommerce.OrderService.repository.OrderRepository;
import com.ecommerce.OrderService.service.OrderService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;

/* This is an integration test unlike the service tests which were unit tests.*/
@SpringBootTest({"server.port=0"})
@EnableConfigurationProperties
@AutoConfigureMockMvc
@ContextConfiguration(classes = {OrderServiceConfig.class})
public class OrderControllerTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MockMvc mockMvc;

    // register wiremock extension
    @RegisterExtension
    static WireMockExtension wireMockServer= WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(8671))
            .build();

    // to change class into json and vice versa
    private ObjectMapper objectMapper= new ObjectMapper()
                    .findAndRegisterModules()
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

//    @BeforeEach
//    void setup(){
//        getProductDetailResponse();
//        reduceQuantity();
//    }

    private void reduceQuantity() {
    }

    private void getProductDetailResponse() throws IOException { // get mock data from product details service

        // GET /product/getById/{id}
        wireMockServer.stubFor(WireMock.get("/product/getById/1")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(StreamUtils.copyToString(OrderControllerTest.class.getClassLoader().getResourceAsStream("mock/GetProduct.json"),
                                Charset.defaultCharset()))));

    }

    @Test
    public void test_when_addOrder_success() {

        // first place order

        // get order by orderid from db and check

        // check output

    }

    @Test
    void getOrderDetails() {
    }
}