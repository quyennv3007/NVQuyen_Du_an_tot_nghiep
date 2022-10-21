package com.convenience_store.service;

import java.util.List;

import com.convenience_store.dto.ProductDTO;
import com.convenience_store.dto.ProductForHomeDTO;
import com.convenience_store.dto.admin.ProductDTO_Admin;
import com.convenience_store.dto.admin.ProductForAdminDTO;
import com.convenience_store.entity.Product;

public interface ProductService {
	List<ProductForHomeDTO> findAll();
	
	List<ProductForAdminDTO> findAll_admin();

	ProductDTO findById(Long id);
	
	Product findProduct(Long id);

	Product create(ProductDTO productDTO);
	
	Product createAdmin(ProductDTO_Admin productDTO);
	
	List<ProductForHomeDTO> findByCategorySub(Long id);
	
	List<ProductForHomeDTO> findByName(String name);
	
//	void update(ProductDTO productDTO);

	void delete(Long id);

	Product updateAdmin(ProductDTO_Admin productDTO, Long id);

	Product findById_Admin(Long id);

	void forbidden(Product product);

	Product findByIdToDelete(Long id);

	void delete(Product product);

	List<ProductForAdminDTO> findAll_Deleted_admin();

	void recover(Product product);

	List<Object[]> getPopularProduct();
}
