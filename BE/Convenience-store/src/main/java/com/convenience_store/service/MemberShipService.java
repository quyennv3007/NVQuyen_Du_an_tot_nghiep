package com.convenience_store.service;

import com.convenience_store.entity.MemberShip;

public interface MemberShipService {
	MemberShip findByType(String type);

	MemberShip findById(Long id);
}
