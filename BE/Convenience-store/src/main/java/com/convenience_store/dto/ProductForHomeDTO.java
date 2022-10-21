package com.convenience_store.dto;

import java.io.Serializable;

import com.convenience_store.entity.CategorySub;
import com.convenience_store.entity.UnitType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductForHomeDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String picture;
	private String description;
	private Double starAveraged;
	private UnitType unitType;
	private CategorySub categorySub;
	private ProductDetailsDTO productDetailsDTO;
	private DiscountDTO discountDTO;
}
