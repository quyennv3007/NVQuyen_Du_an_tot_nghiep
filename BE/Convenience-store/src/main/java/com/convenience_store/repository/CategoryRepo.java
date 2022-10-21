package com.convenience_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.convenience_store.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

}
