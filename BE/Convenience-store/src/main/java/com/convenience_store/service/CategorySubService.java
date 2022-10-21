package com.convenience_store.service;

import java.util.List;

import com.convenience_store.dto.CategorySubDTO;
import com.convenience_store.entity.Category;
import com.convenience_store.entity.CategorySub;

public interface CategorySubService {
	List<CategorySub> findAll();

	CategorySub create(CategorySub categorySub);
	
	List<CategorySubDTO> findByCategory(Category category);
	
	Long getCategoryID(String name);
	
	CategorySub findById(Long id);
}
