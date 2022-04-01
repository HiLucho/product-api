package com.example.productapi.controller;

import com.example.productapi.entity.Product;
import com.example.productapi.service.ProductService;
import com.example.productapi.utils.UtilsTesting;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    private static final String RQ_PRODUCT_POST = "request/postRq.json";
    private static final String RQ_PUT_POST = "request/putRq.json";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    public void testGetRequest() throws Exception {

        Product product = Product.builder()
                .name("nameTest")
                .price(BigDecimal.valueOf(1243.98))
                .sku("FAL-1011122")
                .size("XL")
                .principalImage("https://images.com")
                .brand("testBrand")
                .build();

        when(service.findBySku(anyString())).thenReturn(product);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/product/FAL-1011122")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("FAL-1011122"))
                .andReturn();
    }

    @Test
    public void testGetRequestNotFound() throws Exception {
        when(service.findBySku(anyString())).thenReturn(null);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/product/FAL-123456")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(containsString("Product not found.")))
                .andReturn();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        Product product = Product.builder()
                .name("nameTest")
                .price(BigDecimal.valueOf(1243.98))
                .sku("FAL-1011122")
                .size("XL")
                .principalImage("https://images.com")
                .brand("testBrand")
                .build();

        when(service.findAllProducts()).thenReturn(List.of(product));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andReturn();
    }

    @Test
    public void testPostRequest() throws Exception {

        Product product = new ObjectMapper().readValue(UtilsTesting.readRS(RQ_PRODUCT_POST), Product.class);

        when(service.saveProduct(any())).thenReturn(product);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UtilsTesting.readRS(RQ_PRODUCT_POST));
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.sku").value("AAA-1000010"));
    }

    @Test
    public void testPutRequest() throws Exception {

        Product productUpdated = new ObjectMapper().readValue(UtilsTesting.readRS(RQ_PUT_POST), Product.class);

        when(service.saveProduct(any())).thenReturn(productUpdated);

        RequestBuilder request = MockMvcRequestBuilders.put("/api/v1/product/AAA-1000010")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UtilsTesting.readRS(RQ_PUT_POST));
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testDeleteProduct() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/product/AAA-1000010");
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());

        verify(service, times(1)).deleteProduct(anyString());
    }

}