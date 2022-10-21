package com.convenience_store.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.convenience_store.entity.UnitType;
import com.convenience_store.exception.ResourceNotFoundException;
import com.convenience_store.service.UnitTypeService;

@RestController
@CrossOrigin
@RequestMapping("/v1/api")
public class UnitTypeApi {
	@Autowired
	private UnitTypeService unitTypeService;
	
	@GetMapping("/unit_type")
	public ResponseEntity<?> getAllUnitType(){
		List<UnitType> unitType = unitTypeService.findAll();
		if (unitType == null) {
			throw new ResourceNotFoundException("Not Found unit types !!!");
		}
		return ResponseEntity.ok(unitType);
	}
}
