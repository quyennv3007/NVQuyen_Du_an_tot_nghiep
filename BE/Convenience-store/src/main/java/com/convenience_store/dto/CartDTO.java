package com.convenience_store.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Timestamp date;
	private String customerNotes;
	private String phone;
	private String address;
	private Double totalPayment = 0D;
	private Double totalPaymentNet = 0D;
	private String status;
	private Long userId;
	private List<CartDetailDTO> detailedInvoice = new ArrayList<>();
}
