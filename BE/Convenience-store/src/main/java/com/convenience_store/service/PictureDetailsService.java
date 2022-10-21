package com.convenience_store.service;

import java.util.List;

import com.convenience_store.dto.PictureDetailsDTO;
import com.convenience_store.entity.PictureDetails;
import com.convenience_store.entity.Product;

public interface PictureDetailsService {
	List<PictureDetailsDTO> findAll(Product product, Boolean isDeleted); 
	
	PictureDetails save(PictureDetails PictureDetails);

	List<PictureDetails> saveAll(List<PictureDetails> pictureDetails);

	void deleteByProductID(Long id);

}
