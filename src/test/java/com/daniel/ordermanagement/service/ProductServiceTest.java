package com.daniel.ordermanagement.service;

import com.daniel.ordermanagement.dto.ProductRequestDto;
import com.daniel.ordermanagement.dto.ProductResponseDto;
import com.daniel.ordermanagement.entity.Product;
import com.daniel.ordermanagement.exception.ResourceNotFoundException;
import com.daniel.ordermanagement.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreateProductSuccessfully() {
        ProductRequestDto request = new ProductRequestDto();
        request.setName("Monitor");
        request.setDescription("Monitor 24 pulgadas");
        request.setPrice(new BigDecimal("199.99"));
        request.setStock(5);
        request.setActive(true);

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            product.setId(1L);
            return product;
        });

        ProductResponseDto response = productService.createProduct(request);

        assertNotNull(response);
        assertEquals("Monitor", response.getName());
        assertEquals(new BigDecimal("199.99"), response.getPrice());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(999L));

        verify(productRepository).findById(999L);
    }
}
