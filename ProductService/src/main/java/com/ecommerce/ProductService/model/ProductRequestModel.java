package com.ecommerce.ProductService.model;

import lombok.Data;

@Data
public class ProductRequestModel {

    private String productName;
    private long price;
    private long quantity;

}
