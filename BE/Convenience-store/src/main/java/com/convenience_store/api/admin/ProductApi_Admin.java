package com.convenience_store.api.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.convenience_store.dto.ProductDTO;
import com.convenience_store.dto.admin.ProductDTO_Admin;
import com.convenience_store.dto.admin.ProductForAdminDTO;
import com.convenience_store.exception.ResourceNotFoundException;
import com.convenience_store.security.payload.MessageResponse;
import com.convenience_store.service.ProductService;
import com.convenience_store.service.WorkingLogService;

@CrossOrigin
@RestController
@RequestMapping("/v1/api/admin")
public class ProductApi_Admin {
	
	@Autowired private ProductService productService;
	@Autowired private WorkingLogService workingLogService;
	
	@GetMapping("/product")
	public ResponseEntity<?> getAllProduct() {
		List<ProductForAdminDTO> productDTO = productService.findAll_admin();
		if (productDTO.isEmpty()) {
			throw new ResourceNotFoundException("Not Found Products!!!");
		}
		return ResponseEntity.ok(productDTO);
	}
	
	@GetMapping("/productDeleted")
	public ResponseEntity<?> getAllDeletedProduct() {
		List<ProductForAdminDTO> productDTO = new ArrayList<ProductForAdminDTO>();
		try {
			productDTO = productService.findAll_Deleted_admin();
		} catch (Exception e) {
			return ResponseEntity.ok(new MessageResponse("empty"));
		}
		if (productDTO.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse("empty"));
		}
		return ResponseEntity.ok(productDTO);
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProductByID(@PathVariable("id") Long id) {
		ProductDTO productDTO = productService.findById(id);
		if (Objects.isNull(productDTO)) {
			throw new ResourceNotFoundException("Not Found Products With Id = " + id + " !!!");
		}
		return ResponseEntity.ok(productDTO);
	}
	
	@PostMapping("/product")
	public ResponseEntity<?>addProduct(@RequestBody ProductDTO_Admin productDTO,@RequestHeader("UserId") Long UserId){
		workingLogService.create(UserId,"Thêm mới sản phẩm","Table Product");
		return ResponseEntity.ok(productService.createAdmin(productDTO));
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<?>updateProduct(@RequestBody ProductDTO_Admin productDTO,@PathVariable("id") Long id,@RequestHeader("UserId") Long UserId){
		System.out.println(productDTO);
		workingLogService.create(UserId,"Cập nhật sản phẩm có Id: "+id,"Table Product");
		return ResponseEntity.ok(productService.updateAdmin(productDTO,id));
	}
	
}
