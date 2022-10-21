package com.convenience_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.convenience_store.entity.PictureDetails;
import com.convenience_store.entity.Product;

public interface PictureDetailsRepo extends JpaRepository<PictureDetails, Long> {
	List<PictureDetails> findByProductAndIsDeleted(Product product, Boolean isDeleted);

	@Transactional
	@Modifying
	@Query("Delete from PictureDetails where productID = ?1")
	void deleteByProductID(Long id);
}
