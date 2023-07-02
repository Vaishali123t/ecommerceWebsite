package com.ecommerce.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private long id;
    private long quantity;
    private long amount;
    private Instant orderDate;
    private String orderStatus;
    private ProductDetails productDetails;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDetails {

        private long productId;
        private String productName;
        private long price;
        private long quantity;
    }

}
