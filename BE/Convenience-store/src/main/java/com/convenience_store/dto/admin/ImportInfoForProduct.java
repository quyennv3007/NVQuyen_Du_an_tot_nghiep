package com.convenience_store.dto.admin;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportInfoForProduct implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Double priceImport;
	private Integer quantityImport;
	private String status;
}
