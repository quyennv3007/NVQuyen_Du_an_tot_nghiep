package com.convenience_store.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.dto.CategorySubDTO;
import com.convenience_store.entity.Category;
import com.convenience_store.entity.CategorySub;
import com.convenience_store.repository.CategorySubRepo;
import com.convenience_store.service.CategorySubService;

@Service
public class CategorySubServiceimpl implements CategorySubService {

	@Autowired
	private CategorySubRepo repo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public List<CategorySub> findAll() {
		return repo.findAll();
	}

	@Transactional
	@Override
	public CategorySub create(CategorySub categorySub) {
		return repo.saveAndFlush(categorySub);
	}

	@Override
	public List<CategorySubDTO> findByCategory(Category category) {
		List<CategorySub> categorySub = repo.findByCategory(category);
		List<CategorySubDTO> listCategorySub = new ArrayList<>();
		for (CategorySub item : categorySub) {
			CategorySubDTO categorySubDTO = mapper.map(item, CategorySubDTO.class);
			listCategorySub.add(categorySubDTO);
		}
		return listCategorySub;
	}

	@Override
	public Long getCategoryID(String name) {
		return repo.getCategoryID(name);
	}
	
	@Override
	public CategorySub findById(Long id) {
		return repo.findById(id).get();
	}

}
