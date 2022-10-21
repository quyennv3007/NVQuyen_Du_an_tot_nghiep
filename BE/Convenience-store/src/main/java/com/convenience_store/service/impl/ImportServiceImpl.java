package com.convenience_store.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.dto.admin.ImportDTO_Admin;
import com.convenience_store.dto.admin.ImportInfoDTO_Admin;
import com.convenience_store.dto.admin.ProductOfImportInfoDTO;
import com.convenience_store.entity.Import;
import com.convenience_store.entity.ImportInfo;
import com.convenience_store.entity.ProductDetails;
import com.convenience_store.repository.ImportRepo;
import com.convenience_store.service.ImportInfoService;
import com.convenience_store.service.ImportService;
import com.convenience_store.service.ProductDetailsService;

@Service
public class ImportServiceImpl implements ImportService {

	@Autowired
	private ImportRepo repo;

	@Autowired
	private ImportInfoService importInfoService;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private ProductDetailsService productDetailsService;
	
	@Override
	public List<ImportDTO_Admin> findAll() {
		List<Import> importList = repo.findAll();
		if(importList == null) {
			return null;
		}
		List<ImportDTO_Admin> importDTO_Admin = importList.stream().map(item -> mapper.map(item, ImportDTO_Admin.class))
				.collect(Collectors.toList());

		for (ImportDTO_Admin itemImportDTO : importDTO_Admin) {
			if(itemImportDTO!=null) {
				List<ImportInfo> listImportInfo = importInfoService.findByImportID(itemImportDTO.getId());
				List<ImportInfoDTO_Admin> listImportInfoDTO_Admin = new ArrayList<>();
				for(ImportInfo itemImportInfo : listImportInfo) {
					ProductOfImportInfoDTO productOfImportInfoDTO = new ProductOfImportInfoDTO(itemImportInfo.getProduct().getName(), itemImportInfo.getProduct().getPicture());
					ImportInfoDTO_Admin importInfoDTO_Admin;
					if(itemImportInfo.getStatus() == 2) {
						importInfoDTO_Admin = new ImportInfoDTO_Admin(itemImportInfo.getId(), itemImportInfo.getPriceImport(), itemImportInfo.getQuantityImport(), itemImportDTO.getId(), itemImportInfo.getProduct().getId(), itemImportInfo.getStatus(),null, productOfImportInfoDTO);
					}else {
						ProductDetails productDetails = productDetailsService.findByImportInfo(itemImportInfo);
						importInfoDTO_Admin = new ImportInfoDTO_Admin(itemImportInfo.getId(), itemImportInfo.getPriceImport(), itemImportInfo.getQuantityImport(), itemImportDTO.getId(), itemImportInfo.getProduct().getId(), itemImportInfo.getStatus(),productDetails, productOfImportInfoDTO);
					}
					listImportInfoDTO_Admin.add(importInfoDTO_Admin);
				}
				itemImportDTO.setImportInfo(listImportInfoDTO_Admin);
			}
		}

		return importDTO_Admin;
	}

	@Override
	public Boolean existsById(String id) {
		return repo.existsById(id);
	}

	@Override
	public Import save(Import importData) {
		return repo.save(importData);
	}

}
