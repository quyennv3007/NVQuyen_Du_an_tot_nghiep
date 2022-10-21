package com.convenience_store.service.impl;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.dto.DiscountDTO;
import com.convenience_store.entity.Discount;
import com.convenience_store.entity.Product;
import com.convenience_store.repository.DiscountRepo;
import com.convenience_store.service.DiscountService;
import com.convenience_store.service.ProductService;

@Service
public class DiscountServiceImpl implements DiscountService {

	@Autowired
	private DiscountRepo repo;

	@Autowired
	private ProductService productService;

	@Autowired
	private ModelMapper mapper;

	@Override
	public DiscountDTO findByProductId(Long id) {
		Discount discount = repo.findByProductId(id);
		if (Objects.nonNull(discount)) {
			return mapper.map(discount, DiscountDTO.class);
		}
		return null;
	}

	@Transactional
	@Override
	public void create(DiscountDTO discountDTO) {
		Product product = productService.findProduct(discountDTO.getProductID());
		if (Objects.nonNull(product)) {
			Discount discount = mapper.map(discountDTO, Discount.class);
			discount.setProduct(product);
			repo.saveAndFlush(discount);
		}
	}

	@Transactional
	@Override
	public List<Discount> saveAll(List<Discount> discount) {
		return repo.saveAll(discount);
	}

}
