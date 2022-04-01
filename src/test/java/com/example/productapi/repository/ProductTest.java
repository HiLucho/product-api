package com.example.productapi.repository;

import com.example.productapi.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductTest {

    @Autowired
    private ProductRepository repository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void testCreateProduct() {
        Product product = Product.builder()
                .name("nameTest")
                .price(BigDecimal.valueOf(1243.98))
                .sku("FAL-1011122")
                .size("XL")
                .principalImage("https://images.com")
                .brand("testBrand")
                .build();
        Product savedProduct = repository.save(product);
        Assertions.assertNotNull(savedProduct);
    }

    @Test
    @Order(2)
    public void testFindProductBySku() {
        String sku = "FAL-1011122";
        Product product = repository.findBySku(sku);
        Assertions.assertEquals(product.getSku(), sku);
    }

    @Test
    @Order(3)
    public void testFindProductBySkuNotExist() {
        String sku = "FAL-0000000";
        Product product = repository.findBySku(sku);
        Assertions.assertNull(product);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void testUpdateProduct() {
        String sku = "FAL-1011122";
        Product product = repository.findBySku(sku);

        Product productToUpdate = Product.builder()
                .name("newName")
                .price(BigDecimal.valueOf(99999.90))
                .sku("FAL-1011122")
                .size("newSize")
                .principalImage("https://new-image.com")
                .brand("newbrand")
                .build();

         productToUpdate.setId(product.getId());
         product.setName(productToUpdate.getName());
         product.setPrice(BigDecimal.valueOf(99999.90));
         product.setSize(product.getSize());
         product.setPrincipalImage(product.getPrincipalImage());
         product.setBrand(product.getBrand());

        Product productUpdated = repository.save(product);

        Assertions.assertEquals(product, productUpdated);
    }

    @Test
    @Order(5)
    public void testListProducts(){
        List<Product> productList = repository.findAll();
        Assertions.assertTrue(productList.size() > 0);
    }

    @Test
    @Order(6)
    @Rollback(value = false)
    public void testDeleteProduct(){
        String sku = "FAL-1011122";
        Product productToDelete = repository.findBySku(sku);
        boolean isExistBeforeDelete = Optional.ofNullable(productToDelete).isPresent();
        repository.deleteById(productToDelete.getId());
        boolean isExistAfterDelete = Optional.ofNullable(repository.findBySku(sku)).isPresent();
        Assertions.assertTrue(isExistBeforeDelete);
        Assertions.assertFalse(isExistAfterDelete);
    }
}
