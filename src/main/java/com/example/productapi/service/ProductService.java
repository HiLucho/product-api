package com.example.productapi.service;

import com.example.productapi.entity.Product;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product);
    Product findBySku(String sku);
    List<Product> findAllProducts();
    Product updateProduct(Product product);
    void deleteProduct(String sku);
}
