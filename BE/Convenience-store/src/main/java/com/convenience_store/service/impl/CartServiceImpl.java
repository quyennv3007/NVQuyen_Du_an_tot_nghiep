package com.convenience_store.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.dto.CartDTO;
import com.convenience_store.dto.CartDetailDTO;
import com.convenience_store.entity.Invoice;
import com.convenience_store.entity.InvoiceDetails;
import com.convenience_store.entity.User;
import com.convenience_store.service.CartService;
import com.convenience_store.service.InvoiceDetailsService;
import com.convenience_store.service.InvoiceService;
import com.convenience_store.service.ProductDetailsService;
import com.convenience_store.service.UserService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private InvoiceDetailsService invoiceDetailsService;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private ProductDetailsService productDetailService;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserService userService;

	@Transactional
	@Override
	public void saveCart(CartDTO cartDTO) throws Exception {
		Invoice invoice = mapper.map(cartDTO, Invoice.class);
		User user = userService.findById(cartDTO.getUserId());
		invoice.setUser(user);
		try {
			// insert invoice to DB
			invoice = invoiceService.insert(invoice);
			for (CartDetailDTO item : cartDTO.getDetailedInvoice()) {
				// insert invoiceDetail to DB
				item.setInvoiceId(invoice.getId());
				invoiceDetailsService.insert(item);
				// update quantity product
//				ProductDetails productDetail = productDetailService.findById(item.getDetailedProductId());
//				Integer newQuantity = productDetail.getQuantity() - item.getQuantity();
//				productDetailService.updateQuantity(newQuantity, productDetail.getId());
			}
		} catch (Exception e) {
			throw new Exception("Cannot insert to DB");
		}
	}

	@Override
	public List<CartDTO> getPayment(Long userId) {
		List<Invoice> invoice = invoiceService.findByUserId(userId);
		if(invoice == null) {
			return null;
		}
		List<CartDTO> cartDTO = new ArrayList<>();
		for(Invoice invoiceItem : invoice) {
			CartDTO cartItem = mapper.map(invoiceItem, CartDTO.class);
			cartItem.setUserId(invoiceItem.getUser().getId());
			List<InvoiceDetails> invoiceDetails = invoiceDetailsService.findByInvoice(invoiceItem);
			List<CartDetailDTO> cartDetailTemp = invoiceDetails.stream().map(item -> mapper.map(item, CartDetailDTO.class)).collect(Collectors.toList());
			List<CartDetailDTO> cartDetail = new ArrayList<>();
			for(CartDetailDTO cartDetailItem : cartDetailTemp) {
				for(InvoiceDetails item : invoiceDetails) {
					if(cartDetailItem.getId() == item.getId()){
						cartDetailItem.setName(item.getDetailedProduct().getProduct().getName());
						cartDetail.add(cartDetailItem);
					}
				}
			}
//			IntStream.range(0, cartDetailTemp.size())
//			.forEach(i->{
//				CartDetailDTO cart = cartDetailTemp.get(i);
//				int index = invoiceDetails.stream().map(e->e.getId()).collect(Collectors.toList())
//						.indexOf(cart.getId());
//				if(index !=-1) {
//					cart.setName(invoiceDetails.get(index).getDetailedProduct().getProduct().getName());
//					cartDetailTemp.set(i, cart);
//				}
//			});
			cartItem.setDetailedInvoice(cartDetail);
			cartDTO.add(cartItem);
		}
		if (cartDTO.isEmpty()) {
			return null;
		}
		return cartDTO;
	}

	@Override
	public List<CartDTO> getAllPayment() {
		List<Invoice> invoice = invoiceService.findAll();
		if(invoice == null) {
			return null;
		}
		List<CartDTO> cartDTO = new ArrayList<>();
		for(Invoice invoiceItem : invoice) {
			CartDTO cartItem = mapper.map(invoiceItem, CartDTO.class);
			cartItem.setUserId(invoiceItem.getUser().getId());
			List<InvoiceDetails> invoiceDetails = invoiceDetailsService.findByInvoice(invoiceItem);
			List<CartDetailDTO> cartDetailTemp = invoiceDetails.stream().map(item -> mapper.map(item, CartDetailDTO.class)).collect(Collectors.toList());
			List<CartDetailDTO> cartDetail = new ArrayList<>();
			for(CartDetailDTO cartDetailItem : cartDetailTemp) {
				for(InvoiceDetails item : invoiceDetails) {
					if(cartDetailItem.getId() == item.getId()){
						cartDetailItem.setName(item.getDetailedProduct().getProduct().getName());
						cartDetail.add(cartDetailItem);
					}
				}
			}
//			IntStream.range(0, cartDetailTemp.size())
//			.forEach(i->{
//				CartDetailDTO cart = cartDetailTemp.get(i);
//				int index = invoiceDetails.stream().map(e->e.getId()).collect(Collectors.toList())
//						.indexOf(cart.getId());
//				if(index !=-1) {
//					cart.setName(invoiceDetails.get(index).getDetailedProduct().getProduct().getName());
//					cartDetailTemp.set(i, cart);
//				}
//			});
			cartItem.setDetailedInvoice(cartDetail);
			cartDTO.add(cartItem);
		}
		if (cartDTO.isEmpty()) {
			return null;
		}
		return cartDTO;
	}

}
