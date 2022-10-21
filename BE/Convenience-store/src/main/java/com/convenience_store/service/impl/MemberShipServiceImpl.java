package com.convenience_store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.entity.MemberShip;
import com.convenience_store.repository.MemberShipRepo;
import com.convenience_store.service.MemberShipService;

@Service
public class MemberShipServiceImpl implements MemberShipService {

	@Autowired
	private MemberShipRepo repo;

	@Override
	public MemberShip findByType(String type) {
		return repo.findByType(type);
	}

	@Override
	public MemberShip findById(Long id) {
		return repo.findById(id).get();
	}

}
