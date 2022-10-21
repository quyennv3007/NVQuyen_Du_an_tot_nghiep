package com.convenience_store.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.dto.RatingDTO;
import com.convenience_store.entity.Authority;
import com.convenience_store.entity.Product;
import com.convenience_store.entity.Rating;
import com.convenience_store.entity.Role;
import com.convenience_store.entity.User;
import com.convenience_store.repository.RatingRepo;
import com.convenience_store.service.AuthorityService;
import com.convenience_store.service.RatingService;
import com.convenience_store.service.RoleService;
import com.convenience_store.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RatingServiceImpl implements RatingService{
	
	@Autowired
	private RatingRepo repo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService authService;

//	@Override
//	public List<RatingDTO> findByProductId(Long id) {
//		List<Rating> rating = repo.findByProductID(id);
//		if(!rating.isEmpty()) {
//			return rating.stream().map(item -> mapper.map(item, RatingDTO.class)).collect(Collectors.toList());
//		}
//		return null;
//	}
	
	@Transactional
	@Override
	public void insert(JsonNode ratingDTO) {
		User userResponse = new User();
		ObjectMapper objectMapper = new ObjectMapper();
		Timestamp date = objectMapper.convertValue(ratingDTO.get("date"), Timestamp.class);
		String comment = objectMapper.convertValue(ratingDTO.get("comment"), String.class);
		Integer star = objectMapper.convertValue(ratingDTO.get("star"), Integer.class);
		User user = objectMapper.convertValue(ratingDTO.get("user"), User.class);
		Long productId = objectMapper.convertValue(ratingDTO.get("productId"), Long.class);
		if(user.getId() == null) {
			Role userRole = roleService.findByName("guest");
			Authority author = new Authority(userRole, user);
			userResponse = userService.createAnonymous(user);
			authService.create(author);
		}else {
			userResponse = userService.findById(user.getId());
		}
		try {
			repo.insert(date, comment, star, productId, userResponse.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<RatingDTO> findByProduct(Product product) {
		List<Rating> rating = repo.findByProduct(product);
		if(!rating.isEmpty()) {
			return rating.stream().map(item -> mapper.map(item, RatingDTO.class)).collect(Collectors.toList());
		}
		return null;
	}
}
