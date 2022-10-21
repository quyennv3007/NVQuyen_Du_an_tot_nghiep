package com.convenience_store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.entity.Role;
import com.convenience_store.repository.RoleRepo;
import com.convenience_store.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepo repo;
	
	@Override
	public Role findByName(String name) {
		return repo.findByName(name);
	}

}
