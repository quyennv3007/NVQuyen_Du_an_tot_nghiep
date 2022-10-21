package com.convenience_store.service;

import java.util.List;

public interface WorkingLogService {

	void create(Long userId, String action, String entity);

	List<Object[]> getWorkLog();

}
