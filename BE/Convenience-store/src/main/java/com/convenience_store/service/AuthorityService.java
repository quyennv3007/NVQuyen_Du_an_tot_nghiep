package com.convenience_store.service;

import java.util.List;

import com.convenience_store.dto.AuthorityDTO;
import com.convenience_store.entity.Authority;
import com.convenience_store.entity.User;

public interface AuthorityService {
	void create(Authority authority);
	
	List<AuthorityDTO> findByUser(User user);

	void create_Admin(List<Authority> authorities);

	void removeByUserId(Long id);
}
