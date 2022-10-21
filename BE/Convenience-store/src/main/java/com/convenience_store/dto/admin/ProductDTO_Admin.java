package com.convenience_store.dto.admin;

import java.io.Serializable;
import java.util.List;

import com.convenience_store.entity.CategorySub;
import com.convenience_store.entity.Discount;
import com.convenience_store.entity.PictureDetails;
import com.convenience_store.entity.ProductDetails;
import com.convenience_store.entity.ProductInfo;
import com.convenience_store.entity.UnitType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO_Admin implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String picture;
	private String description;
	private String brand;
	private String origin;
	private Boolean isDeleted;
	private CategorySub categorySub;
	private UnitType unitType;
	private List<ProductInfo> productInfo;
	private List<Discount> discount;
	private List<ProductDetails>detailedProduct;
	private List<PictureDetails>detailedPicture;
	
}
