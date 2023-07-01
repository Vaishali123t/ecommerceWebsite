package com.ecommerce.ProductService.service;

import com.ecommerce.ProductService.entity.Product;
import com.ecommerce.ProductService.exception.ProductServiceException;
import com.ecommerce.ProductService.model.ProductRequestModel;
import com.ecommerce.ProductService.model.ProductResponseModel;
import com.ecommerce.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ProductServiceImplementation implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<ProductResponseModel> getAllProducts() {

        List<Product> productEntity= productRepository.findAll();
        List<ProductResponseModel> product= productEntity
                            .stream()
                            .map(p->{
                                ProductResponseModel pm= new ProductResponseModel();
                                BeanUtils.copyProperties(p,pm);
                                return pm;
                            }).toList();


        return product;
    }

    @Override
    public long saveEmployee(ProductRequestModel product) {

        Product productEntity= new Product();
        log.info("Adding product");
        BeanUtils.copyProperties(product,productEntity);

        productRepository.save(productEntity);
        log.info("Product created");
        return productEntity.getProductId();
    }

    @Override
    public ProductResponseModel getProductById(long id){

        log.info("get product by id "+ id);
        ProductResponseModel product= new ProductResponseModel();
        Product productEntity= productRepository.findById(id)
                .orElseThrow(()-> new ProductServiceException("Product with given id not found","PRODUCT_NOT_FOUND"));
        BeanUtils.copyProperties(productEntity,product);

        return product;
    }

    @Override
    public void updateQuantity(long productId, long quantity) {

        log.info("Reduce quantity {} for Id {}",quantity,productId);
        // check whether productId is present or not
        Product product= productRepository.findById(productId)
                .orElseThrow(()->new ProductServiceException("Product with given id not found","PRODUCT_NOT_FOUND"));

        // if productId is present check whether required quantity is there or not
        if(product.getQuantity()<quantity) throw new ProductServiceException("Required Quantity is greater than quantity present","REQUIRED_QUANTITY_NOT_AVAILABLE");

        product.setQuantity(product.getQuantity()-quantity);
        log.info("Product quantity reduced");
        productRepository.save(product);

    }
}
