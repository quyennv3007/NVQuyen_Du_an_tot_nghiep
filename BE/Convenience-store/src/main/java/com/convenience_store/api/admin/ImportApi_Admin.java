package com.convenience_store.api.admin;

import java.util.List;
import java.util.stream.Collectors;

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

import com.convenience_store.dto.admin.ImportDTO_Admin;
import com.convenience_store.entity.Import;
import com.convenience_store.entity.ImportInfo;
import com.convenience_store.entity.Product;
import com.convenience_store.entity.ProductDetails;
import com.convenience_store.exception.ResourceNotFoundException;
import com.convenience_store.security.payload.MessageResponse;
import com.convenience_store.service.ImportInfoService;
import com.convenience_store.service.ImportService;
import com.convenience_store.service.ProductDetailsService;
import com.convenience_store.service.ProductService;
import com.convenience_store.service.WorkingLogService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin
@RestController
@RequestMapping("/v1/api/admin")
public class ImportApi_Admin {
	@Autowired private ImportService importService;
	@Autowired private ImportInfoService importInfoService;
	@Autowired private ProductService productService;
	@Autowired private ProductDetailsService productDetailsService;
	@Autowired private ObjectMapper objectMapper;
	@Autowired private WorkingLogService workingLogService;
	
	@GetMapping("/import")
	public ResponseEntity<?> getAllImport() {
		List<ImportDTO_Admin> importDTO_Admin = importService.findAll();
		if (importDTO_Admin.isEmpty()) {
			throw new ResourceNotFoundException("Not Found Products!!!");
		}
		return ResponseEntity.ok(importDTO_Admin);
	}
	@PostMapping("/import")
	public ResponseEntity<?> addImport(@RequestBody JsonNode importData,@RequestHeader("UserId") Long UserId){
		try {
			System.out.println(importData);
			Import import_ = objectMapper.convertValue(importData, Import.class);
			Import importResult = importService.save(import_);
			
			TypeReference<List<ImportInfo>> type = new TypeReference<List<ImportInfo>>() {};
			List<ImportInfo> importInfo = objectMapper.convertValue(importData.get("importInfo"), type)
				.stream().peek(o->{
					o.setImportID(importResult);
					o.setProduct(productService.findById_Admin(o.getProduct().getId()));
				}).collect(Collectors.toList());
			importInfoService.saveAll(importInfo);
			workingLogService.create(UserId,"Thêm mới Nhập hàng có Id: "+importResult.getId(),"Table Import");
			System.out.println(importInfo);

			return ResponseEntity.ok(new MessageResponse("ok"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse("Error"));
		}
	}
	@PostMapping("check/importID")
	public ResponseEntity<?> checkImportID(@RequestBody String id) {
		try {
			if (importService.existsById(id.toUpperCase())) {
				return ResponseEntity.ok(new MessageResponse("ImportID is existed!"));
			} else {
				return ResponseEntity.ok(new MessageResponse("ImportID is not existed!"));
			}
		} catch (Exception e) {
			return ResponseEntity.ok(new MessageResponse("Error"));
		}
	}
	
	@PutMapping("/import/{importID}")
	public ResponseEntity<?> updateImport(@RequestBody Import importData,@PathVariable("importID")String importID,@RequestHeader("UserId") Long UserId){
		try {
			importService.save(importData);
			workingLogService.create(UserId,"Cập nhật thông tin Nhập hàng có Id: "+importID,"Table Import");
			System.out.println(importData);
			return ResponseEntity.ok(new MessageResponse("OK"));
		} catch (Exception e) {
			return ResponseEntity.ok(new MessageResponse("Error"));
		}
	}
	
	@PutMapping("/importInfo/{importInfoID}")
	public ResponseEntity<?> updateImportInfo(@RequestBody ImportInfo importInfoData,@PathVariable("importInfoID")Long importInfoID,@RequestHeader("UserId") Long UserId){
		try {
			ImportInfo temp = importInfoService.findById(importInfoID);
			importInfoData.setImportID(temp.getImportID());
			importInfoData.setProduct(temp.getProduct());
			importInfoService.save(importInfoData);
			workingLogService.create(UserId,"Cập nhật (ImportInfo) có Id: "+temp.getId(),"Table ImportInfo");
			System.out.println(importInfoData);
			return ResponseEntity.ok(new MessageResponse("OK"));
		} catch (Exception e) {
			return ResponseEntity.ok(new MessageResponse("Error"));
		}
	}
	
	@DeleteMapping("/importInfo/{importInfoID}")
	public ResponseEntity<?>deleteImportInfoByID(@PathVariable("importInfoID")Long importInfoID,@RequestHeader("UserId") Long UserId){
		try {
			System.out.println(importInfoID);
			ImportInfo importInfo = importInfoService.findById(importInfoID);
			importInfo.setStatus(4L);
			importInfoService.save(importInfo);
			workingLogService.create(UserId,"Thu hồi thông tin Nhập hàng (ImportInfo) Id: "+importInfoID,"Table ImportInfo");
			Product product = productService.findById_Admin(importInfo.getProduct().getId());
			product.setIsDeleted(true);
			productService.forbidden(product);
			
			List<ProductDetails> productDetails = productDetailsService.findByProductId(importInfo.getProduct().getId());
			for (ProductDetails item : productDetails) {
				item.setIsDeleted(true);
				item.setAvailable(false);
			}
			productDetailsService.forbidden(productDetails);
			return ResponseEntity.ok(new MessageResponse("OK"));
		} catch (Exception e) {
			return ResponseEntity.ok(new MessageResponse("Error"));
		}
	}
	
	
}
