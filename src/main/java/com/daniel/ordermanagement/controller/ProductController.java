package com.daniel.ordermanagement.controller;

import com.daniel.ordermanagement.dto.ProductRequestDto;
import com.daniel.ordermanagement.dto.ProductResponseDto;
import com.daniel.ordermanagement.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing products.
 *
 * Provides endpoints for:
 * - Creating, updating, and deleting products
 * - Retrieving product information
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto createProduct(@Valid @RequestBody ProductRequestDto request) {
        return productService.createProduct(request);
    }

    @GetMapping
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id,
                                            @Valid @RequestBody ProductRequestDto request) {
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
