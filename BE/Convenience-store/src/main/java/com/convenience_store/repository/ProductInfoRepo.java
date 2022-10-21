package com.convenience_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.convenience_store.entity.Product;
import com.convenience_store.entity.ProductInfo;

public interface ProductInfoRepo extends JpaRepository<ProductInfo, Long> {
	ProductInfo findByProduct(Product product);
}
