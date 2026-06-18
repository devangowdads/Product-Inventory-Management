package com.ecommerce.controller;

import com.ecommerce.dto.ProductResponseDto;
import com.ecommerce.dto.StockRequestDto;
import com.ecommerce.serviceinterface.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Inventory Controller", description = "APIs for managing product inventory")
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

	private final ProductService productService;
	private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

	
	@Operation(summary = "Increase product stock", description = "Increases the stock quantity of a product by its ID")
	@PatchMapping("/{id}/increase")
	public ResponseEntity<ProductResponseDto> increaseStock( @PathVariable Long id, @Valid @RequestBody StockRequestDto requestDto) {
		
		logger.info("Received request to increase stock for product id: {} by quantity: {}",id, requestDto.getQuantity());
		ProductResponseDto response = productService.increaseStock(id, requestDto);
		logger.info("Stock increased successfully for product id: {}", id);
		return ResponseEntity.ok(response);
	}

	
    @Operation(summary = "Decrease product stock", description = "Decreases the stock quantity of a product by its ID")
	@PatchMapping("/{id}/decrease")
	public ResponseEntity<ProductResponseDto> decreaseStock(@PathVariable Long id,@Valid @RequestBody StockRequestDto requestDto) {
		
		logger.info("Received request to decrease stock for product id: {} by quantity: {}",id, requestDto.getQuantity());
		ProductResponseDto response = productService.decreaseStock(id, requestDto);
		logger.info("Stock decrease successfully for product id: {}", id);
		return ResponseEntity.ok(response);
	}

    @Operation(summary = "Get low stock products", description = "Returns all products whose stock is below the configured threshold")
	@GetMapping("/low-stock")
	public ResponseEntity<List<ProductResponseDto>> getLowStockProducts() {

		List<ProductResponseDto> products = productService.getLowStockProducts();
		return ResponseEntity.ok(products);
	}
}