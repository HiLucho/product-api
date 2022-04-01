package com.example.productapi.controller;

import com.example.productapi.entity.Product;
import com.example.productapi.model.MessageResponse;
import com.example.productapi.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * This class refers to CRUD for Product API
 *
 */
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    /**
     * Create a product
     * @param product
     * @return product
     */
    @PostMapping(value = "/product")
    public ResponseEntity<?> createNewProduct(@RequestBody @Validated Product product)  {
        Product  createdProduct  = productService.saveProduct(product);
            return new ResponseEntity<Object>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * Get a simple product by sku
     * @param sku
     * @return product
     */
    @GetMapping(value = "/product/{sku}")
    public ResponseEntity<?> getProductById(@PathVariable("sku") String sku) {
        Product product = productService.findBySku(sku);
        if(Objects.isNull(product)) {
            MessageResponse.MessageResponseBuilder messageResponse = MessageResponse.builder();
            messageResponse.message("Product not found.");
            return new ResponseEntity<Object>(messageResponse.build(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(product, HttpStatus.OK);
    }

    /**
     * Retrieve a list of all products
     * @return List<product></>
     */
    @GetMapping(value = "/product")
    public ResponseEntity<?> getAllProducts() {
        List<Product> userList = productService.findAllProducts();
        return new ResponseEntity<Object>(userList, HttpStatus.OK);
    }

    /**
     * Update a product by sku
     * @param sku
     * @param product
     * @return product
     */
    @PutMapping(value = "/product/{sku}")
    public ResponseEntity<?> updateProduct(@PathVariable("sku") String sku, @Validated @RequestBody Product product) {
        product.setSku(sku);
        Product updatedProduct = productService.updateProduct(product);
        return new ResponseEntity<Object>(updatedProduct, HttpStatus.OK);
    }

    /**
     * Delete a product by sku
     * @param sku
     * @return void
     */
    @DeleteMapping(value = "/product/{sku}")
    public ResponseEntity<?> deleteProduct(@PathVariable("sku") String sku) {
        productService.deleteProduct(sku);
        MessageResponse.MessageResponseBuilder messageResponse = MessageResponse.builder();
        messageResponse.message("Product has been deleted successfully.");
        return new ResponseEntity<Object>(messageResponse.build(), HttpStatus.OK);
    }

}
