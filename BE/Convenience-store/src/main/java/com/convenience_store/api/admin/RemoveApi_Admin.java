package com.convenience_store.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.convenience_store.entity.Invoice;
import com.convenience_store.entity.Product;
import com.convenience_store.entity.User;
import com.convenience_store.service.InvoiceDetailsService;
import com.convenience_store.service.InvoiceService;
import com.convenience_store.service.ProductDetailsService;
import com.convenience_store.service.ProductService;
import com.convenience_store.service.UserService;
import com.convenience_store.service.WorkingLogService;

@CrossOrigin
@RestController
@RequestMapping("/v1/api/admin")
public class RemoveApi_Admin {
	@Autowired private InvoiceService invoiceService;
	@Autowired private InvoiceDetailsService invoiceDetailsService;
	@Autowired private ProductDetailsService productDetailsService;
	@Autowired private ProductService productService;
	@Autowired private UserService userService;
	@Autowired private WorkingLogService workingLogService;
	
	@DeleteMapping("/invoice/{id}")
	public ResponseEntity<?>removeInvoiceById(@PathVariable("id")Long id,@RequestHeader("UserId") Long UserId){
		Invoice invoice = invoiceService.findById(id);
		invoice.setStatus("Delete");
		invoiceService.delete(invoice);
		workingLogService.create(UserId,"Xoá hoá đơn có Id: "+invoice.getId(),"Table Invoice");
		return null;
	}
	
	@DeleteMapping("/account/{id}")
	public ResponseEntity<?>removeAccountById(@PathVariable("id")Long id,@RequestHeader("UserId") Long UserId){
		User user = userService.findById(id);
		user.setIsDeleted(true);
		userService.delete(user);
		workingLogService.create(UserId,"Xoá tài khoản có Id: "+user.getId(),"Table User");
		return null;
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<?>deleteProduct(@PathVariable("id")Long id,@RequestHeader("UserId") Long UserId){
		Product product = productService.findByIdToDelete(id);
		product.setIsDeleted(true);
		productService.delete(product);
		workingLogService.create(UserId,"Xoá sản phẩm có Id: "+id,"Table Product");
		return null;
	}
}
