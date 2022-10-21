package com.convenience_store.service;

import java.util.List;

import com.convenience_store.dto.RatingDTO;
import com.convenience_store.entity.Product;
import com.convenience_store.entity.Rating;
import com.fasterxml.jackson.databind.JsonNode;

public interface RatingService {
	List<RatingDTO> findByProduct(Product product);
	
	void insert(JsonNode ratingDTO);
}
