package com.convenience_store.api.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.convenience_store.dto.CartDTO;
import com.convenience_store.entity.Invoice;
import com.convenience_store.entity.InvoiceDetails;
import com.convenience_store.entity.ProductDetails;
import com.convenience_store.exception.ResourceNotFoundException;
import com.convenience_store.security.payload.MessageResponse;
import com.convenience_store.service.CartService;
import com.convenience_store.service.InvoiceDetailsService;
import com.convenience_store.service.InvoiceService;
import com.convenience_store.service.ProductDetailsService;
import com.convenience_store.service.WorkingLogService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@CrossOrigin
@RequestMapping("/v1/api/admin")
public class InvoiceApi_Admin {
	
	@Autowired private CartService cartService;
	@Autowired private InvoiceService invoiceService;
	@Autowired private InvoiceDetailsService invoiceDetailsService;
	@Autowired private ProductDetailsService productDetailsService;
	@Autowired private WorkingLogService workingLogService;
	
	@GetMapping("/payment")
	public ResponseEntity<?> getAllPayment() {
		List<CartDTO> cartDTO = cartService.getAllPayment();
		if (cartDTO == null) {
			throw new ResourceNotFoundException("Not Found payment !!!");
		}
		return ResponseEntity.ok(cartDTO);
	}
	
	@GetMapping("/invoice/totalMoneyById/{invoiceId}")
	public Map<String,List<Object[]>>getTotalMoneyById(@PathVariable("invoiceId")Long invoiceId){
		System.out.println("run");
		Map<String,List<Object[]>> map = new HashMap<>();
		map.put("totalMoneyById", invoiceService.getTotalMoneyById(invoiceId));
		return map;
	}
	
	@PutMapping("/invoice/updateItem/{id}")
	public ResponseEntity<?>updateInvoiceByItem(@RequestBody JsonNode itemData, @PathVariable("id")Long invoiceId,@RequestHeader("UserId") Long UserId){
		System.out.println(itemData);
		
		double memberShipDiscount = memberShipCalculate(itemData.get("memberShip").asText());
		long detailedInvoiceId = itemData.get("id").asLong();
		int quantity = itemData.get("quantity").asInt();
		double price = itemData.get("price").asDouble();
		
		//update detailedInvoice by Id
		InvoiceDetails item = invoiceDetailsService.findById(detailedInvoiceId);
		item.setQuantity(quantity);
		item.setPrice(price);
		invoiceDetailsService.update(item);
		//update detailedInvoice by Id edge
		
		//update Invoice totalPayment NetPayment
		updateInvoiceTotalPayment_NetPayment(invoiceId, memberShipDiscount);
		workingLogService.create(UserId,"Cập nhật hoá đơn có Id: "+item.getId(),"Table DetailedInvoice");
		return null;
	}
	@PutMapping("/invoice/updateStatus/{invoiceID}")
	public ResponseEntity<?>updateInvoiceByStatus(@RequestBody JsonNode statusData, @PathVariable("invoiceID")Long invoiceId,@RequestHeader("UserId") Long UserId){
		System.out.println(statusData);
		String status = statusData.get("status").asText();
		Invoice invoice = invoiceService.findById(invoiceId);
		try {
			if(status.equals("Paid")&& invoice.getStatus().equalsIgnoreCase(status)) {
				return ResponseEntity.ok(new MessageResponse("Bạn đã cập nhật sang trạng thái Paid trước đó"));
			}else{
				invoice.setStatus(status);
				invoiceService.update(invoice);	
				Invoice invoiceAfterUpdateStatus = invoiceService.findById(invoiceId);
				if(invoiceAfterUpdateStatus.getStatus().equalsIgnoreCase("Paid")) {
					productDetailsService.updateQuantity(invoice);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// update quantity product and update status importInfo
		workingLogService.create(UserId,"Cập nhật trạng thái hoá đơn ["+status+"] - Id: "+invoice.getId(),"Table Invoice");
		return null;
	}
	
	@PutMapping("/invoice/deleteItem/{detailedInvoiceId}")
	public ResponseEntity<?>updateInvoiceByDeletingItem(@RequestBody JsonNode memberShipData, @PathVariable("detailedInvoiceId")Long detailedInvoiceId,@RequestHeader("UserId") Long UserId){
		System.out.println(memberShipData);
		double memberShipDiscount = memberShipCalculate(memberShipData.get("memberShip").asText());
		InvoiceDetails item = invoiceDetailsService.findById(detailedInvoiceId);
		invoiceDetailsService.delete(item);
		
		updateInvoiceTotalPayment_NetPayment(item.getInvoice().getId(), memberShipDiscount);
		workingLogService.create(UserId,"Xoá hoá đơn có Id: "+item.getId(),"Table DetailedInvoice");
		return null;
	}
	
	@PutMapping("/invoice/addItem/{memberShipType}")
	public ResponseEntity<?>addItemIntoInvoice(@RequestBody InvoiceDetails invoiceDetails,@PathVariable("memberShipType")String memberShipType,@RequestHeader("UserId") Long UserId){
		System.out.println(invoiceDetails);
		
		Long invoiceID = invoiceDetails.getInvoice().getId();
		Invoice invoice = invoiceService.findById(invoiceID);
		ProductDetails productDetails = productDetailsService.findById(invoiceDetails.getDetailedProduct().getId());
		
		invoiceDetails.setInvoice(invoice);
		invoiceDetails.setDetailedProduct(productDetails);
		
		invoiceDetailsService.save(invoiceDetails);
		
		updateInvoiceTotalPayment_NetPayment(invoiceID, memberShipCalculate(memberShipType));
		workingLogService.create(UserId,"Thêm sản phẩm vào hoá đơn có Id: "+invoiceID,"Table Invoice");
		return null;
	}
	
	public void updateInvoiceTotalPayment_NetPayment(Long invoiceId,double memberShipDiscount) {
		Invoice invoice = invoiceService.findById(invoiceId);
		
		List<InvoiceDetails> list = invoiceDetailsService.findByInvoice(invoice);
		double totalPayment = list.stream().mapToDouble(e->e.getPrice()*e.getQuantity()*(1-e.getDiscount())).sum();
		double totalPaymentNet = totalPayment*(1-memberShipDiscount);
		
		invoice.setTotalPayment(totalPayment);
		invoice.setTotalPaymentNet(totalPaymentNet);
		
		invoiceService.update(invoice);
		
	}
	
	public double memberShipCalculate(String memberShipType) {
		if(memberShipType.equals("Gold")) {
			return 0.02;
		}
		if(memberShipType.equals("Platinum")) {
			return 0.05;
		}
		return 0d;
	}
}
