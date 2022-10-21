package com.convenience_store.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer quantity;
	private Double priceSell;
	private Timestamp dateRelease;
	private Timestamp dateEnd;
	private Boolean available;
}
