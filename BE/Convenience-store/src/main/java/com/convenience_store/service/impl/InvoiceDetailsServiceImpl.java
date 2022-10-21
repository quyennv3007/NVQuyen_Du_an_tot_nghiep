package com.convenience_store.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.dto.CartDetailDTO;
import com.convenience_store.entity.Invoice;
import com.convenience_store.entity.InvoiceDetails;
import com.convenience_store.repository.InvoiceDetailsRepo;
import com.convenience_store.service.InvoiceDetailsService;

@Service
public class InvoiceDetailsServiceImpl implements InvoiceDetailsService {

	@Autowired
	private InvoiceDetailsRepo repo;
	
	@Transactional
	@Override
	public void insert(CartDetailDTO cartDetailDTO) {
		repo.insert(cartDetailDTO);
	}

	@Override
	public List<InvoiceDetails> findByInvoice(Invoice invoice) {
		return repo.findByInvoice(invoice);
	}

	@Override
	public InvoiceDetails findById(Long detailedInvoiceId) {
		return repo.findById(detailedInvoiceId).get();
	}

	@Override
	public void update(InvoiceDetails item) {
		repo.saveAndFlush(item);
	}

	@Override
	public void delete(InvoiceDetails item) {
		repo.delete(item);
	}

	@Override
	public void save(InvoiceDetails invoiceDetails) {
		repo.save(invoiceDetails);
	}
}
