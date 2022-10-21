package com.convenience_store.service;

import java.util.List;

import com.convenience_store.entity.Invoice;

public interface InvoiceService {
	Invoice insert(Invoice invoice); 
	List<Invoice> findAll();
	List<Invoice> findByUserId(Long userId);
	Invoice findById(Long invoiceId);
	void update(Invoice invoice);
	void delete(Invoice invoice);
	List<Object[]> getTotalMoneyById(Long invoiceId);
}
