package com.convenience_store.service;

import java.util.List;

import com.convenience_store.dto.admin.ImportDTO_Admin;
import com.convenience_store.entity.Import;

public interface ImportService {
	List<ImportDTO_Admin> findAll();

	Boolean existsById(String id);

	Import save(Import importData);
}
