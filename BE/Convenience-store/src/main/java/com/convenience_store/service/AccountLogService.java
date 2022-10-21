package com.convenience_store.service;

import java.util.List;

public interface AccountLogService {

	void create(Long userId, String action, String description);

	List<Object[]> getAccLog();

}
