package com.convenience_store.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.dto.CategoryDTO;
import com.convenience_store.dto.CategorySubDTO;
import com.convenience_store.entity.Category;
import com.convenience_store.repository.CategoryRepo;
import com.convenience_store.service.CategoryService;
import com.convenience_store.service.CategorySubService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo repo;
	
	@Autowired
	private CategorySubService categorySubService;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public List<CategoryDTO> findAll() {
		List<Category> category = repo.findAll();
		if (category == null) {
			return null;
		}
		List<CategoryDTO> ListCategoryDTO = new ArrayList<>();
		for(Category item : category) {
			CategoryDTO categoryDTO = mapper.map(item, CategoryDTO.class);
			List<CategorySubDTO> categorySubDTO = categorySubService.findByCategory(item);
			if(categorySubDTO != null && !categorySubDTO.isEmpty()) {
				categoryDTO.setCategorySubDTO(categorySubDTO);
			}
			ListCategoryDTO.add(categoryDTO);
		}
		return ListCategoryDTO;
	}
	
	@Transactional
	@Override
	public Category create(Category category) {
		return repo.saveAndFlush(category);
	}

	@Override
	public void update(Category category) {
		repo.saveAndFlush(category);
	}

	@Override
	public Category findById(Long id) {
		return repo.findById(id).get();
	}

}
