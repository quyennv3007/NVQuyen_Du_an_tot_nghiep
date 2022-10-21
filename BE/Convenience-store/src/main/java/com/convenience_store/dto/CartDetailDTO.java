package com.convenience_store.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer quantity;
	private Double price;
	private Double discount;
	private String name;
	private Long invoiceId;
	private Long detailedProductId;
}
