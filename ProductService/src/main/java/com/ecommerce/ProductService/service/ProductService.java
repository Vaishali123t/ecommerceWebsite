package com.ecommerce.ProductService.service;


import com.ecommerce.ProductService.model.ProductRequestModel;
import com.ecommerce.ProductService.model.ProductResponseModel;

import java.util.List;

public interface ProductService {

    List<ProductResponseModel> getAllProducts();
    long saveEmployee(ProductRequestModel product);

    ProductResponseModel getProductById(long id);

    void updateQuantity(long productId, long quantity);
}
