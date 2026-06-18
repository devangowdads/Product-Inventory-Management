package com.ecommerce.controller;

import com.ecommerce.dto.ProductRequestDto;
import com.ecommerce.dto.ProductResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.serviceinterface.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@Tag(name = "Product Controller", description = "APIs for managing products")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


	@Operation(summary = "Create a new product", description = "Creates and saves a new product")
	@PostMapping
	public ResponseEntity<ProductResponseDto> createProduct( @Valid @RequestBody ProductRequestDto requestDto) {

		logger.info("Received request to create product: {}", requestDto.getName());
		ProductResponseDto response = productService.createProduct(requestDto);
		logger.info("Product created successfully with id: {}", response.getId());
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}


	@Operation(summary = "Update product", description = "Updates an existing product by ID")
	@PutMapping("/{id}")
	public ResponseEntity<ProductResponseDto> updateProduct( @PathVariable Long id, @Valid @RequestBody ProductRequestDto requestDto) {

		logger.info("Received request to update product with id: {}", id);
		ProductResponseDto response = productService.updateProduct(id, requestDto);
		logger.info("Product updated successfully with id: {}", id);
		return ResponseEntity.ok(response);
	}


	@Operation(summary = "Get product by ID", description = "Fetches a product using its ID")
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {

		logger.info("Fetching product with id: {}", id);
		ProductResponseDto response = productService.getProductById(id);
		logger.info("Product fetched successfully with id: {}", id);
		return ResponseEntity.ok(response);
	}



	@Operation(summary = "Get all products", description = "Returns a list of all available products")
	@GetMapping
	public ResponseEntity<Page<ProductResponseDto>> getAllProducts(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {

		logger.info("Fetching all products");
		return ResponseEntity.ok(productService.getAllProducts(page, size));

		

		}
	}

