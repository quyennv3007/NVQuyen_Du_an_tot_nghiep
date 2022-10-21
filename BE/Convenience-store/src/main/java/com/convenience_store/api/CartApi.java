package com.convenience_store.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.convenience_store.dto.CartDTO;
import com.convenience_store.exception.ResourceNotFoundException;
import com.convenience_store.service.CartService;

@CrossOrigin
@RestController
@RequestMapping("/v1/api")
public class CartApi {

	@Autowired
	private CartService cartService;

	@PostMapping("/cart")
	public ResponseEntity<?> addCart(@RequestBody CartDTO cartDTO) {
		try {
			cartService.saveCart(cartDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@GetMapping("/payment/{userId}")
	public ResponseEntity<?> getPayment(@PathVariable("userId") Long userId) {
		List<CartDTO> cartDTO = cartService.getPayment(userId);
		if (cartDTO == null) {
			throw new ResourceNotFoundException("Not Found payment of " + userId +" !!!");
		}
		return ResponseEntity.ok(cartDTO);
	}
}
