package com.convenience_store.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.convenience_store.entity.Product;
import com.convenience_store.entity.Rating;

public interface RatingRepo extends JpaRepository<Rating, Long> {
	@Query(value = "SELECT * FROM rating WHERE productID = ?", nativeQuery = true)
	List<Rating> findByProductID(Long id);
	
	List<Rating> findByProduct(Product product);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT INTO rating(date, comment, star, productID, userID) "
			+ "VALUES(?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
	void insert(Timestamp date, String comment, Integer star, Long productId, Long userId);
}
