package com.ecommerce.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductResponseDto {

    private Long id;

    private String name;

    private String category;

    private BigDecimal price;

    private Integer quantity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

   
}
