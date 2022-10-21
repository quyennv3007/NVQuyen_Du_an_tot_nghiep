package com.convenience_store.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.convenience_store.dto.DiscountDTO;
import com.convenience_store.service.DiscountService;

@RestController
@CrossOrigin
@RequestMapping("/v1/api")
public class DiscountApi {
	
	@Autowired
	private DiscountService discountService;
	
	@PostMapping("/discount")
	public ResponseEntity<?> addDiscount(@RequestBody DiscountDTO discountDTO){
		try {
			discountService.create(discountDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
}
