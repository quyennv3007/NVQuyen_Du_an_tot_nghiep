package com.convenience_store.service;

import java.util.List;

import com.convenience_store.dto.CartDetailDTO;
import com.convenience_store.entity.Invoice;
import com.convenience_store.entity.InvoiceDetails;

public interface InvoiceDetailsService {
	void insert(CartDetailDTO cartDetailDTO);
	List<InvoiceDetails> findByInvoice(Invoice invoice);
	InvoiceDetails findById(Long detailedInvoiceId);
	void update(InvoiceDetails item);
	void delete(InvoiceDetails item);
	void save(InvoiceDetails invoiceDetails);
}
