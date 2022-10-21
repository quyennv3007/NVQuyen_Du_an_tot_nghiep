package com.convenience_store.service;

import java.util.List;

import com.convenience_store.entity.ImportInfo;

public interface ImportInfoService {
	ImportInfo findByStatusAndProductID(Integer status, Long id);

	List<ImportInfo> findByImportID(String importID);
	
	ImportInfo findByProductID(Long id);

	ImportInfo findById(Long importInfoID);

	void save(ImportInfo importInfoData);

	void saveAll(List<ImportInfo> importInfo);
	
	void updateStatus(Long status, Long id);
}
