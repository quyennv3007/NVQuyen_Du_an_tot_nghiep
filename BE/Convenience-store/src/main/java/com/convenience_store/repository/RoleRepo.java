package com.convenience_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.convenience_store.entity.Role;

public interface RoleRepo extends JpaRepository<Role, String> {
	Role findByName(String name);
}
