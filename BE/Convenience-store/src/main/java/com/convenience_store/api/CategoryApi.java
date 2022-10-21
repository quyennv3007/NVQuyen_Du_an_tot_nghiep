package com.convenience_store.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.convenience_store.dto.CategoryDTO;
import com.convenience_store.entity.Category;
import com.convenience_store.exception.ResourceNotFoundException;
import com.convenience_store.service.CategoryService;

@CrossOrigin
@RestController
@RequestMapping("/v1/api")
public class CategoryApi {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/category")
	public ResponseEntity<?> getAllCategory(){
		List<CategoryDTO> listCategory = categoryService.findAll();
		if(listCategory.isEmpty()) {
			throw new ResourceNotFoundException("Not Found Category");
		}
		return ResponseEntity.ok(listCategory);
	}
	
	@PostMapping("/category")
	public ResponseEntity<?> addCategory(@RequestBody Category category){
		try {
			categoryService.create(category);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@PutMapping("/category")
	public ResponseEntity<?> updateCategory(@RequestBody Category category){
		try {
			categoryService.update(category);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
}
