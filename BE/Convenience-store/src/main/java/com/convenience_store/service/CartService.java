package com.convenience_store.service;

import java.util.List;

import com.convenience_store.dto.CartDTO;

public interface CartService {
	void saveCart(CartDTO cartDTO) throws Exception;
	
	List<CartDTO> getPayment(Long userId);
	
	List<CartDTO> getAllPayment();
}
