package com.convenience_store.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.entity.Product;
import com.convenience_store.entity.ProductInfo;
import com.convenience_store.repository.ProductInfoRepo;
import com.convenience_store.service.ProductInfoService;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

	@Autowired
	private ProductInfoRepo repo;
	
	@Override
	public ProductInfo findByProduct(Product product) {
		return repo.findByProduct(product);
	}
	
	@Transactional
	@Override
	public ProductInfo save(ProductInfo productInfo) {
		return repo.saveAndFlush(productInfo);
	}
	
	@Transactional
	@Override
	public List<ProductInfo> saveAll(List<ProductInfo> ProductInfo) {
		return repo.saveAllAndFlush(ProductInfo);
	}

}
