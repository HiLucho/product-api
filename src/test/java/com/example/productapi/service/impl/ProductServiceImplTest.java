package com.example.productapi.service.impl;

import com.example.productapi.entity.OtherImage;
import com.example.productapi.entity.Product;
import com.example.productapi.repository.ProductRepository;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ProductServiceImplTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @InjectMocks
    private ProductServiceImpl service;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void beforeEachTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveProduct() {

        Product product = Product.builder()
                .name("nameTest")
                .price(BigDecimal.valueOf(1243.98))
                .sku("FAL-1011122")
                .size("XL")
                .principalImage("https://images.com")
                .brand("testBrand")
                .build();
        when(productRepository.saveAndFlush(any())).thenReturn(product);
        when(productRepository.save(any())).thenReturn(product);
        Product savedProduct = service.saveProduct(product);
        Assertions.assertEquals(savedProduct, product);
    }

    @Test
    void TestFindBySku() {
        Product product = Product.builder()
                .name("nameTest")
                .price(BigDecimal.valueOf(1243.98))
                .sku("FAL-1011122")
                .size("XL")
                .principalImage("https://images.com")
                .brand("testBrand")
                .build();
        when(productRepository.findBySku(anyString())).thenReturn(product);
        Product foundProduct = service.findBySku("FAL-1011122");
        Assertions.assertEquals(foundProduct, product);
    }

    @Test
    void TestFindAllProducts() {
        Product product = Product.builder()
                .name("nameTest")
                .price(BigDecimal.valueOf(1243.98))
                .sku("FAL-1011122")
                .size("XL")
                .principalImage("https://images.com")
                .brand("testBrand")
                .build();
        when(productRepository.findAll()).thenReturn(List.of(product));
        List<Product> foundProduct = service.findAllProducts();
        Assertions.assertEquals(foundProduct.size(), 1);
    }

    @Test
    void TestUpdateProduct() {
        Product product = Product.builder()
                .id(1L)
                .name("nameTest")
                .price(BigDecimal.valueOf(1243.98))
                .sku("FAL-1011122")
                .size("XL")
                .principalImage("https://images.com")
                .brand("testBrand")
                .otherImages(List.of(OtherImage.builder().id(1L).otherImage("https://other-images.com").build()))
                .build();
        Product productUpdate = Product.builder()
                .id(1L)
                .name("newName")
                .price(BigDecimal.valueOf(99999.99))
                .sku("FAL-1011122")
                .size("XXL")
                .principalImage("https://new-images.com")
                .brand("newBrand")
                .build();
        when(productRepository.findBySku(anyString())).thenReturn(product);
        when(productRepository.save(any())).thenReturn(productUpdate);
        Product productUpdated = service.updateProduct(productUpdate);
        Assertions.assertEquals(productUpdated, productUpdate);
    }

    @Test
    void TestDeleteProduct() {
        Product product = Product.builder()
                .id(1L)
                .name("nameTest")
                .price(BigDecimal.valueOf(1243.98))
                .sku("FAL-1011122")
                .size("XL")
                .principalImage("https://images.com")
                .brand("testBrand")
                .build();
        doNothing().when(productRepository).deleteById(any());
        when(productRepository.findBySku(anyString())).thenReturn(product);
        service.deleteProduct("FAL-1011122");
        verify(productRepository).findBySku("FAL-1011122");
        verify(productRepository).deleteById(1L);
        verifyNoMoreInteractions(productRepository);
    }
}