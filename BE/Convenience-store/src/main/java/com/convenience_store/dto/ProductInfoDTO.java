package com.convenience_store.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String weight;
	private String guide;
	private String note;
	private String component;
	private String capacity;
	private String usage;
	private String others;
}
