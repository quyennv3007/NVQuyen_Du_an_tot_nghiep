package com.convenience_store.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.entity.ImportInfo;
import com.convenience_store.entity.Invoice;
import com.convenience_store.entity.InvoiceDetails;
import com.convenience_store.entity.Product;
import com.convenience_store.entity.ProductDetails;
import com.convenience_store.exception.ResourceNotFoundException;
import com.convenience_store.repository.ProductDetailsRepo;
import com.convenience_store.service.ImportInfoService;
import com.convenience_store.service.InvoiceDetailsService;
import com.convenience_store.service.ProductDetailsService;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {

	@Autowired
	private ProductDetailsRepo repo;
	
	@Autowired
	private InvoiceDetailsService invoiceDetailsService;
	
	@Autowired
	private ProductDetailsService productDetailsService;
	
	@Autowired
	private ImportInfoService importInfoService;
	
	@Transactional
	@Override
	public void updateImportInfo(Long importInfoID, Long productID) {
		repo.updateImportInfo(importInfoID, productID);
	}

	@Transactional
	@Override
	public ProductDetails save(ProductDetails productDetails) {
		productDetails.setIsDeleted(Boolean.FALSE);
		return repo.saveAndFlush(productDetails);
	}

	@Override
	public ProductDetails findById(Long id) {
		Optional<ProductDetails> productDetails = repo.findById(id);
		if (productDetails.isEmpty()) {
			throw new ResourceNotFoundException("Cannot Found ProductDetail by id");
		}
		return productDetails.get();
	}

	@Override
	public void updateQuantity(Invoice invoice) {
		List<InvoiceDetails> invoiceDetails = invoiceDetailsService.findByInvoice(invoice);
		for(InvoiceDetails itemInvoiceDetails : invoiceDetails) {
			ProductDetails productDetail = productDetailsService.findById(itemInvoiceDetails.getDetailedProduct().getId());
			Integer newQuantity = productDetail.getQuantity() - itemInvoiceDetails.getQuantity();
			System.out.println("quantity:"+newQuantity);
			System.out.println("detailedId:"+itemInvoiceDetails.getDetailedProduct().getId());
			if(newQuantity < 0) {
				newQuantity = 0;
				repo.updateQuantity(newQuantity, itemInvoiceDetails.getDetailedProduct().getId());
				ImportInfo importInfo = importInfoService.findByProductID( itemInvoiceDetails.getDetailedProduct().getId());
				importInfoService.updateStatus(3L, importInfo.getId());
				importInfo = importInfoService.findByStatusAndProductID(2, itemInvoiceDetails.getDetailedProduct().getProduct().getId());
				if(importInfo != null) {
					importInfoService.updateStatus(1L, importInfo.getId());
				}
				System.out.println("run1");
			}else {
				System.out.println("run2");
				repo.updateQuantity(newQuantity, itemInvoiceDetails.getDetailedProduct().getId());
			}
		}
	}

	@Override
	public List<ProductDetails> findAll(Boolean isDeleted, Boolean available) {
		return repo.findByIsDeletedAndAvailable(isDeleted, available);
	}
	
	@Override
	public List<ProductDetails> findAll_admin(Boolean isDeleted) {
		return repo.findByIsDeleted(isDeleted);
	}

	@Override
	public ProductDetails findByProduct(Product product) {
		return repo.findByProduct(product);
	}
	
	@Transactional
	@Override
	public List<ProductDetails> saveAll(List<ProductDetails> productDetails) {
		return repo.saveAllAndFlush(productDetails);
	}

	@Override
	public ProductDetails findByImportInfo(ImportInfo importInfo) {
		return repo.findByImportInfo(importInfo);
	}

	@Override
	public List<ProductDetails> findByProductId(Long id) {
		return repo.findAllProductById(id);
	}

	@Override
	public void forbidden(List<ProductDetails> productDetails) {
		repo.saveAllAndFlush(productDetails);
	}

}
