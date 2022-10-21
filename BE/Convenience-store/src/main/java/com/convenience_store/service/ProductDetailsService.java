package com.convenience_store.service;

import java.util.List;

import com.convenience_store.entity.ImportInfo;
import com.convenience_store.entity.Invoice;
import com.convenience_store.entity.Product;
import com.convenience_store.entity.ProductDetails;

public interface ProductDetailsService {
	List<ProductDetails> findAll_admin(Boolean isDeleted);
	
	List<ProductDetails> findAll(Boolean isDeleted, Boolean available);

	void updateImportInfo(Long importInfoID, Long productID);

	ProductDetails save(ProductDetails productDetails);

	ProductDetails findById(Long id);

	void updateQuantity(Invoice invoice);

	ProductDetails findByProduct(Product product);
	
	ProductDetails findByImportInfo(ImportInfo importInfo);

	List<ProductDetails> saveAll(List<ProductDetails> productDetails);

	List<ProductDetails> findByProductId(Long id);

	void forbidden(List<ProductDetails> productDetails);
}
