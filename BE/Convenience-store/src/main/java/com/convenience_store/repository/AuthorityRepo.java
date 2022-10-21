package com.convenience_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.convenience_store.entity.Authority;
import com.convenience_store.entity.User;

public interface AuthorityRepo extends JpaRepository<Authority, Long> {
	List<Authority> findByUser(User user);

	@Transactional
	@Modifying
	@Query("Delete from Authority where userID = ?1")
	void removeByUserId(Long id);
}
