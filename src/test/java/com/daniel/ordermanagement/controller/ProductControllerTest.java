package com.daniel.ordermanagement.controller;

import com.daniel.ordermanagement.dto.ProductResponseDto;
import com.daniel.ordermanagement.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void shouldReturnAllProducts() throws Exception {
        ProductResponseDto product = new ProductResponseDto(
                1L,
                "Monitor",
                "Monitor 24 pulgadas",
                new BigDecimal("199.99"),
                5,
                true
        );

        given(productService.getAllProducts()).willReturn(List.of(product));

        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Monitor"))
                .andExpect(jsonPath("$[0].price").value(199.99));
    }
}
