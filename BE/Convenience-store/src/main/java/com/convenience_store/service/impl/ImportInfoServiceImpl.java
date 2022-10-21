package com.convenience_store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.entity.ImportInfo;
import com.convenience_store.repository.ImportInfoRepo;
import com.convenience_store.service.ImportInfoService;

@Service
public class ImportInfoServiceImpl implements ImportInfoService {

	@Autowired
	private ImportInfoRepo repo;

	@Override
	public ImportInfo findByStatusAndProductID(Integer status, Long id) {
		return repo.findByStatusAndProductID(status, id);
	}

	@Override
	public List<ImportInfo> findByImportID(String importID) {
		return repo.findByImportID(importID);
	}

	@Override
	public ImportInfo findByProductID(Long id) {
		return repo.findByProductID(id);
	}

	@Override
	public ImportInfo findById(Long importInfoID) {
		// TODO Auto-generated method stub
		return repo.findById(importInfoID).get();
	}

	@Override
	public void save(ImportInfo importInfoData) {
		repo.save(importInfoData);

	}

	@Override
	public void saveAll(List<ImportInfo> importInfo) {
		repo.saveAllAndFlush(importInfo);
	}

	@Override
	public void updateStatus(Long status, Long id) {
		repo.updateStatus(status, id);
	}

}
