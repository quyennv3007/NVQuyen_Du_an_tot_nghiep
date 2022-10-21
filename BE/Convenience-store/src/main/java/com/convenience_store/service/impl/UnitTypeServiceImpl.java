package com.convenience_store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.entity.UnitType;
import com.convenience_store.repository.UnitTypeRepo;
import com.convenience_store.service.UnitTypeService;

@Service
public class UnitTypeServiceImpl implements UnitTypeService{
	
	@Autowired
	private UnitTypeRepo repo;
	
	@Override
	public List<UnitType> findAll() {
		return repo.findAll();
	}
	
	@Override
	public UnitType findById(Long id) {
		return repo.findById(id).get();
	}

}
