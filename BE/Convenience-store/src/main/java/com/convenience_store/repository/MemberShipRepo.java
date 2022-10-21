package com.convenience_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.convenience_store.entity.MemberShip;

public interface MemberShipRepo extends JpaRepository<MemberShip, Long> {
	MemberShip findByType(String type);
}
