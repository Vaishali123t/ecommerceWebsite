package com.ecommerce.ProductService.controller;

import com.ecommerce.ProductService.model.ProductRequestModel;
import com.ecommerce.ProductService.model.ProductResponseModel;
import com.ecommerce.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductResponseModel> getAllProducts(){
        return productService.getAllProducts();
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<ProductResponseModel> getProductById(@PathVariable long id){

        ProductResponseModel productResponse= productService.getProductById(id);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> saveEmployee(@RequestBody ProductRequestModel product){
        long productId= productService.saveEmployee(product);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }


}
