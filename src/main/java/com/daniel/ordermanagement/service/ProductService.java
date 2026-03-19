package com.daniel.ordermanagement.service;

import com.daniel.ordermanagement.dto.ProductRequestDto;
import com.daniel.ordermanagement.dto.ProductResponseDto;
import com.daniel.ordermanagement.entity.Product;
import com.daniel.ordermanagement.exception.ResourceNotFoundException;
import com.daniel.ordermanagement.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Gestiona la lógica de negocio de productos:
 * - Creación de productos
 * - Obtención de todos los productos o por ID
 * - Actualización de productos existentes
 * - Eliminación de productos
 * - Conversión de entidades a DTOs de respuesta
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDto createProduct(ProductRequestDto request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .active(request.getActive())
                .build();

        Product savedProduct = productRepository.save(product);
        return mapToResponse(savedProduct);
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        return mapToResponse(product);
    }

    public ProductResponseDto updateProduct(Long id, ProductRequestDto request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setActive(request.getActive());

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        productRepository.delete(product);
    }

    private ProductResponseDto mapToResponse(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getActive()
        );
    }
}