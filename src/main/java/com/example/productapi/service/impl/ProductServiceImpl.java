package com.example.productapi.service.impl;

import com.example.productapi.entity.Product;
import com.example.productapi.repository.ProductRepository;
import com.example.productapi.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * This is a service to implement JPA provider for Product CRUD.
 */
@Transactional
@Service("productService")
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    /**
     * Save a product
     * @param product
     * @return
     */
    @Override
    public Product saveProduct(Product product) {
        try {
            Product prod = productRepository.saveAndFlush(product);
            return productRepository.save(prod);
        } catch(DataIntegrityViolationException exception){
            throw new DataIntegrityViolationException(Objects.requireNonNull(exception.getMessage()));
        }
    }

    /**
     * Find a product by a sku
     * @param sku
     * @return
     */
    @Override
    public Product findBySku(String sku) {
        return productRepository.findBySku(sku);
    }

    /**
     * Retrieve a list of Products
     * @return List<Product>
     */
    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Product product) {
        Product productToUpdate = productRepository.findBySku(product.getSku());
        productToUpdate.setBrand(product.getBrand());
        productToUpdate.setName(product.getName());
        productToUpdate.setSize(product.getSize());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setPrincipalImage(product.getPrincipalImage());
        productToUpdate.setOtherImages(product.getOtherImages());
        return productRepository.save(productToUpdate);
    }

    /** Delete a product by sku
     * @param sku
     */
    @Override
    public void deleteProduct(String sku) {
        Product productToDelete = productRepository.findBySku(sku);
        productRepository.deleteById(productToDelete.getId());
    }
}
