package com.ecommerce.ProductService.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductRequestModel {

    @NotBlank(message = "The product name is required.")
    private String productName;
    @NotNull(message = "Price is required.")
    @Min(value=1,message = "Price cannot be negative or zero")
    private Long price;
    @NotNull(message = "Quantity is required.")
    @Min(value=1,message = "Price cannot be negative or zero")
    private Long quantity;

}
