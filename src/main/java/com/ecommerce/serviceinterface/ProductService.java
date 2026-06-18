package com.ecommerce.serviceinterface;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ecommerce.dto.ProductRequestDto;
import com.ecommerce.dto.ProductResponseDto;
import com.ecommerce.dto.StockRequestDto;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto requestDto);

    ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto);

    ProductResponseDto getProductById(Long id);

    Page<ProductResponseDto> getAllProducts(int page, int size);
    
    

    ProductResponseDto increaseStock(Long id, StockRequestDto requestDto);

    ProductResponseDto decreaseStock(Long id, StockRequestDto requestDto);

    List<ProductResponseDto> getLowStockProducts();
    
}