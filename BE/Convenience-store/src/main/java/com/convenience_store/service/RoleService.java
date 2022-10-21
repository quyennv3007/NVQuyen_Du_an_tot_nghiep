package com.convenience_store.service;

import com.convenience_store.entity.Role;

public interface RoleService {
	Role findByName(String name);
}
