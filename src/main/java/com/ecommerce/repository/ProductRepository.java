package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

boolean existsByName(String name);

List<Product> findByQuantityLessThan(Integer quantity);

boolean existsByNameAndIdNot(String name, Long id);

}
