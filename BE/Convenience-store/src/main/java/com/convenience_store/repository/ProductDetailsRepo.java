package com.convenience_store.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.convenience_store.entity.ImportInfo;
import com.convenience_store.entity.Product;
import com.convenience_store.entity.ProductDetails;

public interface ProductDetailsRepo extends JpaRepository<ProductDetails, Long> {
	
	List<ProductDetails> findByIsDeletedAndAvailable(Boolean isDeleted, Boolean available);
	
	List<ProductDetails> findByIsDeleted(Boolean isDeleted);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE detailedProduct SET importInfoID = ?1 WHERE productID = ?2", nativeQuery = true)
	void updateImportInfo(Long importInfoID, Long productID);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE detailedProduct SET quantity = ?1 WHERE id = ?2", nativeQuery = true)
	void updateQuantity(Integer quantity, Long productDetailId);
	
	ProductDetails findByProduct(Product product);
	
	ProductDetails findByImportInfo(ImportInfo importInfo);

	
	List<ProductDetails> findAllProductById(Long id);
}
