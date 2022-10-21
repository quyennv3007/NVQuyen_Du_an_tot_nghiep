package com.convenience_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.convenience_store.entity.Invoice;

public interface InvoiceRepo extends JpaRepository<Invoice, Long> {
	
	@Query(value = "SELECT * FROM invoice WHERE userID = ?", nativeQuery = true)
	List<Invoice> findByUserId(Long userId);

	@Query(value = "Select i.totalPayment,i.totalPaymentNet from invoice i where i.id = ?1",nativeQuery=true)
	List<Object[]> getTotalMoneyById(Long invoiceId);
}
