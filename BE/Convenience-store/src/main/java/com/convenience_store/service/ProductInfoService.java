package com.convenience_store.service;

import java.util.List;

import com.convenience_store.entity.Product;
import com.convenience_store.entity.ProductInfo;

public interface ProductInfoService {
	ProductInfo findByProduct(Product product);

	ProductInfo save(ProductInfo ProductInfo);

	List<ProductInfo> saveAll(List<ProductInfo> ProductInfo);
}
