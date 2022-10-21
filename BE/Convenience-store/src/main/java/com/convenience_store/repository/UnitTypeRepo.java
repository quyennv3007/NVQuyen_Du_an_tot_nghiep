package com.convenience_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.convenience_store.entity.UnitType;

public interface UnitTypeRepo extends JpaRepository<UnitType, Long> {

}
