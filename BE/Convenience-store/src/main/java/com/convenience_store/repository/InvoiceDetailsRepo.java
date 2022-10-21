package com.convenience_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.convenience_store.dto.CartDetailDTO;
import com.convenience_store.entity.Invoice;
import com.convenience_store.entity.InvoiceDetails;

public interface InvoiceDetailsRepo extends JpaRepository<InvoiceDetails, Long> {

	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT INTO detailedInvoice(quantity, price, discount, detailedProductID, invoiceID) "
			+ "VALUES(:#{#dto.quantity}, :#{#dto.price}, :#{#dto.discount}, :#{#dto.detailedProductId}, :#{#dto.invoiceId})", nativeQuery = true)
	void insert(@Param("dto") CartDetailDTO dto);
	
	List<InvoiceDetails> findByInvoice(Invoice invoice);
}
