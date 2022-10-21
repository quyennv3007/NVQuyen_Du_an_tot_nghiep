package com.convenience_store.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.convenience_store.entity.Product;
import com.convenience_store.entity.User;
import com.convenience_store.service.ProductService;
import com.convenience_store.service.UserService;
import com.convenience_store.service.WorkingLogService;

@CrossOrigin
@RestController
@RequestMapping("/v1/api/admin")
public class RecoverApi_Admin {
	@Autowired private ProductService productService;
	@Autowired private UserService userService;
	@Autowired private WorkingLogService workingLogService;
	
	@PutMapping("recoverAcc/{id}")
	public ResponseEntity<?>recoverAccount(@PathVariable("id")Long id,@RequestHeader("UserId") Long UserId){
		User user = userService.findById(id);
		user.setIsDeleted(false);
		userService.recover(user);
		workingLogService.create(UserId,"Khôi phục tài khoản có Id: "+id,"Table Users");
		return null;
	}
	
	@PutMapping("recoverProd/{id}")
	public ResponseEntity<?>recoverProduct(@PathVariable("id")Long id,@RequestHeader("UserId") Long UserId){
		Product product = productService.findById_Admin(id);
		product.setIsDeleted(false);
		productService.recover(product);
		workingLogService.create(UserId,"Xoá hoá đơn có Id: "+id,"Table Product");
		return null;
	}
}
