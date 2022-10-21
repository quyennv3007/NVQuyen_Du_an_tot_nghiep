package com.convenience_store.service;

import java.util.List;

import com.convenience_store.dto.DiscountDTO;
import com.convenience_store.entity.Discount;

public interface DiscountService {
	DiscountDTO findByProductId(Long id);

	void create(DiscountDTO discountDTO);

	List<Discount> saveAll(List<Discount> discount);
}
