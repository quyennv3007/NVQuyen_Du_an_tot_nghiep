package com.convenience_store.service;

import java.util.List;

import com.convenience_store.entity.UnitType;

public interface UnitTypeService {
	List<UnitType> findAll();
	
	UnitType findById(Long id);
}
