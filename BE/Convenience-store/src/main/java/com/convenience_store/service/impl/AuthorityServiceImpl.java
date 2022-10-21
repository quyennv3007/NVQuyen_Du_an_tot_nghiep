package com.convenience_store.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.dto.AuthorityDTO;
import com.convenience_store.entity.Authority;
import com.convenience_store.entity.User;
import com.convenience_store.repository.AuthorityRepo;
import com.convenience_store.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService{
	
	@Autowired
	private AuthorityRepo repo;
	@Autowired
	private ModelMapper mapper;
	
	@Transactional
	@Override
	public void create(Authority authority) {
		repo.saveAndFlush(authority);
	}

	@Override
	public List<AuthorityDTO> findByUser(User user) {
		List<Authority> authority = repo.findByUser(user);
		return authority.stream().map(item -> mapper.map(item, AuthorityDTO.class)).collect(Collectors.toList());
	}

	@Override
	public void create_Admin(List<Authority> authorities) {
		repo.saveAll(authorities);
	}

	@Override
	public void removeByUserId(Long id) {
		repo.removeByUserId(id);
		
	}
	
	
}
