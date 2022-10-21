package com.convenience_store.service;

import java.util.List;

import com.convenience_store.dto.CategoryDTO;
import com.convenience_store.entity.Category;

public interface CategoryService {
	List<CategoryDTO> findAll();

	Category create(Category category);
	
	void update(Category category);

	Category findById(Long id);
	
}
