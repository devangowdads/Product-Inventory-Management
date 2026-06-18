package com.ecommerce.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dto.ProductRequestDto;
import com.ecommerce.dto.ProductResponseDto;
import com.ecommerce.dto.StockRequestDto;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.Constants;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.serviceinterface.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

	private static final int LOW_STOCK = 5;

	private final ProductRepository productRepository;

	@Override
	public ProductResponseDto createProduct(ProductRequestDto requestDto) {
		if (productRepository.existsByName(requestDto.getName())) {
            throw new ResourceNotFoundException(Constants.DuplicateProductName+ requestDto.getName());
        }

		Product product = new Product();
		product.setName(requestDto.getName());
		product.setCategory(requestDto.getCategory());
		product.setPrice(requestDto.getPrice());
		product.setQuantity(requestDto.getQuantity());

		Product savedProduct = productRepository.save(product);

		return convertToResponse(savedProduct);
	}

	@Override
	public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto) {

		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.PRODUCT_NOT_FOUND + id));
		
		if (productRepository.existsByNameAndIdNot(requestDto.getName(), id)) {
		    throw new ResourceNotFoundException(Constants.DuplicateProductName+ requestDto.getName());
		}
		product.setName(requestDto.getName());
		product.setCategory(requestDto.getCategory());
		product.setPrice(requestDto.getPrice());
		product.setQuantity(requestDto.getQuantity());

		Product updatedProduct = productRepository.save(product);

		return convertToResponse(updatedProduct);
	}

	@Override
	@Transactional(readOnly = true)
	public ProductResponseDto getProductById(Long id) {

		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.PRODUCT_NOT_FOUND + id));

		return convertToResponse(product);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProductResponseDto> getAllProducts(int page, int size) {

	    Pageable pageable = PageRequest.of(page, size);
	    return productRepository.findAll(pageable).map(this::convertToResponse);
	}

	@Override
	public ProductResponseDto increaseStock(Long id, StockRequestDto requestDto) {

		Product product = productRepository.findById(id)
				.orElseThrow(() ->  new ResourceNotFoundException(Constants.PRODUCT_NOT_FOUND + id));

		product.setQuantity(product.getQuantity() + requestDto.getQuantity());

		Product updatedProduct = productRepository.save(product);

		return convertToResponse(updatedProduct);
	}

	@Override
	public ProductResponseDto decreaseStock(Long id, StockRequestDto requestDto) {

		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.PRODUCT_NOT_FOUND + id));

		if (product.getQuantity() < requestDto.getQuantity()) {
			throw new ResourceNotFoundException(Constants.INSUFFICIENT_STOCK);
		}

		product.setQuantity(product.getQuantity() - requestDto.getQuantity());

		Product updatedProduct = productRepository.save(product);

		return convertToResponse(updatedProduct);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductResponseDto> getLowStockProducts() {

		return productRepository.findByQuantityLessThan(LOW_STOCK)
				.stream()
				.map(this::convertToResponse)
				.collect(Collectors.toList());
	}


	private ProductResponseDto convertToResponse(Product product) {

		ProductResponseDto responseDto = new ProductResponseDto();

		responseDto.setId(product.getId());
		responseDto.setName(product.getName());
		responseDto.setCategory(product.getCategory());
		responseDto.setPrice(product.getPrice());
		responseDto.setQuantity(product.getQuantity());
		responseDto.setCreatedAt(product.getCreatedAt());
		responseDto.setUpdatedAt(product.getUpdatedAt());

		return responseDto;
	}

}
