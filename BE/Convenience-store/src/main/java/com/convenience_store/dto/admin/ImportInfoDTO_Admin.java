package com.convenience_store.dto.admin;

import java.io.Serializable;

import com.convenience_store.entity.ProductDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportInfoDTO_Admin implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Double priceImport;
	private Integer quantityImport;
	private String importID;
	private Long productID;
	private Long status;
	private ProductDetails productDetails;
	private ProductOfImportInfoDTO product;
}
