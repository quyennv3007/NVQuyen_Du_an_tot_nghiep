package com.convenience_store.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.entity.Invoice;
import com.convenience_store.repository.InvoiceRepo;
import com.convenience_store.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceRepo repo;
	
	@Transactional
	@Override
	public Invoice insert(Invoice invoice) {
		return repo.saveAndFlush(invoice);
	}

	@Override
	public List<Invoice> findAll() {
		return repo.findAll();
	}

	@Override
	public List<Invoice> findByUserId(Long userId) {
		return repo.findByUserId(userId);
	}

	@Override
	public Invoice findById(Long invoiceId) {
		return repo.findById(invoiceId).get();
	}

	@Override
	public void update(Invoice invoice) {
		repo.saveAndFlush(invoice);
	}

	@Override
	public void delete(Invoice invoice) {
		repo.saveAndFlush(invoice);
		
	}

	@Override
	public List<Object[]> getTotalMoneyById(Long invoiceId) {
		return repo.getTotalMoneyById(invoiceId);
	}

}
