package com.convenience_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.convenience_store.entity.Import;

public interface ImportRepo extends JpaRepository<Import, String> {

}
