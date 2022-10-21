package com.convenience_store.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.convenience_store.dto.RatingDTO;
import com.convenience_store.service.RatingService;
import com.fasterxml.jackson.databind.JsonNode;

@CrossOrigin
@RestController
@RequestMapping("/v1/api/")
public class RatingApi {
	
	@Autowired
	private RatingService ratingService;
	
	@PostMapping("/rating")
	public ResponseEntity<?> addRating(@RequestBody JsonNode ratingDTO) {
		try {
			ratingService.insert(ratingDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
}
