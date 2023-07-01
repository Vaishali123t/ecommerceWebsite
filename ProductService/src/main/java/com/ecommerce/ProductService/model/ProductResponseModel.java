package com.ecommerce.ProductService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseModel {

    private long productId;
    private String productName;
    private long price;
    private long quantity;
}
