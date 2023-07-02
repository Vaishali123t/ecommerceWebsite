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
public class OrderRequest {

    private long productId;
    private long quantity;
    private long amount;
    private PaymentMode paymentMode;

}
