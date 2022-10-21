package com.convenience_store.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.dto.PictureDetailsDTO;
import com.convenience_store.entity.PictureDetails;
import com.convenience_store.entity.Product;
import com.convenience_store.exception.ResourceNotFoundException;
import com.convenience_store.repository.PictureDetailsRepo;
import com.convenience_store.service.PictureDetailsService;

@Service
public class PictureDetailsServiceImpl implements PictureDetailsService {
	
	@Autowired
	private PictureDetailsRepo repo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public List<PictureDetailsDTO> findAll(Product product, Boolean isDeleted) {
		List<PictureDetails> pictureDetails = repo.findByProductAndIsDeleted(product, isDeleted);
		if(pictureDetails.isEmpty()) {
			throw new ResourceNotFoundException("Cannot Found PictureDetails");
		}
		List<PictureDetailsDTO> pictureDetailsDTO = new ArrayList<>();
		for(PictureDetails item : pictureDetails) {
			PictureDetailsDTO itemDTO = mapper.map(item, PictureDetailsDTO.class);
			pictureDetailsDTO.add(itemDTO);
		}
		return pictureDetailsDTO;
	}

	@Override
	public PictureDetails save(PictureDetails PictureDetails) {
		PictureDetails.setIsDeleted(Boolean.FALSE);
		return repo.save(PictureDetails);
	}

	@Override
	public List<PictureDetails> saveAll(List<PictureDetails> pictureDetails) {
		return repo.saveAll(pictureDetails);
	}

	@Override
	public void deleteByProductID(Long id) {
		repo.deleteByProductID(id);
		
	}

}
