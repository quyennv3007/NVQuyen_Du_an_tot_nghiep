package com.convenience_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.convenience_store.entity.Discount;

public interface DiscountRepo extends JpaRepository<Discount, Long> {
	
	@Query(value ="SELECT TOP 1 * FROM discount WHERE productID = ? Order By id desc" , nativeQuery = true)
	Discount findByProductId(Long productId);
}
