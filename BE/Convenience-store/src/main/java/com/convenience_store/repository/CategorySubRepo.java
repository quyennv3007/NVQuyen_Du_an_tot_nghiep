package com.convenience_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.convenience_store.entity.Category;
import com.convenience_store.entity.CategorySub;

public interface CategorySubRepo extends JpaRepository<CategorySub, Long> {
	List<CategorySub> findByCategory(Category category);
	
	@Query(value = "SELECT categoryID FROM categorySub WHERE name = ?", nativeQuery = true)
	Long getCategoryID(String name);
}
