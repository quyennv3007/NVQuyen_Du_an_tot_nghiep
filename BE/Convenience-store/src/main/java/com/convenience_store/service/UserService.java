package com.convenience_store.service;

import java.util.List;

import com.convenience_store.dto.UserDTO;
import com.convenience_store.entity.User;

public interface UserService {
	List<UserDTO> findAll();

	Boolean existsByPhone(String phone);

	User findById(Long id);

	User findByEmail(String email);

	Boolean existsByEmail(String email);

	User create(User user);

	public User createAnonymous(User user);

	void update(User user) throws NullPointerException;

	void updatePass(String password, Long id);

	void delete(Long id);

	User create_Admin(User user);

	void delete(User user);

	List<UserDTO> findAllDeleted();

	void recover(User user);
}
