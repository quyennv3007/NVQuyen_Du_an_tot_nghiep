package com.convenience_store.dto.admin;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOfImportInfoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String picture;
}
