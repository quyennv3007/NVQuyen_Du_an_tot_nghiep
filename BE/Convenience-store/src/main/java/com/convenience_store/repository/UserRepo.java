package com.convenience_store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.convenience_store.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
	List<User> findByIsDeleted(Boolean isDeleted);

	Optional<User> findByPhone(String phone);
	
	User findByEmail(String email);

	Boolean existsByPhone(String phone);

	Boolean existsByEmail(String email);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE users SET name = ?1, email = ?2, phone = ?3, address = ?4, gender = ?5, photo = ?6 WHERE id = ?7", nativeQuery = true)
	void updateProfile(String name, String email, String phone, String address, Boolean gender,String photo, Long id);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE users SET password = ?1  WHERE id = ?2", nativeQuery = true)
	void updatePass(String password, Long id);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE users SET isDeleted = 1 WHERE id = ?", nativeQuery = true)
	void deleteLogical(Long id);
}
