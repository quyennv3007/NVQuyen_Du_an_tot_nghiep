package com.convenience_store.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.convenience_store.dto.ProductDTO;
import com.convenience_store.dto.ProductForHomeDTO;
import com.convenience_store.exception.ResourceNotFoundException;
import com.convenience_store.service.GGDriveService;
import com.convenience_store.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.drive.model.File;

@CrossOrigin
@RestController
@RequestMapping("/v1/api")
public class ProductApi {

	@Autowired
	private ProductService productService;

	@Autowired
	private GGDriveService googleDriveService;

	@GetMapping("/product")
	public ResponseEntity<?> getAllProduct() {
		List<ProductForHomeDTO> productDTO = productService.findAll();
		if (productDTO.isEmpty()) {
			throw new ResourceNotFoundException("Not Found Products!!!");
		}
		return ResponseEntity.ok(productDTO);
	}
	
	@GetMapping("/product/popular")
	public Map<String,List<Object[]>>getPopularProduct(){
		Map<String,List<Object[]>> map = new HashMap<>();
		map.put("popularProduct", productService.getPopularProduct());
		return map;
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProductByID(@PathVariable("id") Long id) {
		ProductDTO productDTO = productService.findById(id);
		if (Objects.isNull(productDTO)) {
			throw new ResourceNotFoundException("Not Found Products With Id = " + id + " !!!");
		}
		return ResponseEntity.ok(productDTO);
	}
	
	@GetMapping("/product/categorysub/{id}")
	public ResponseEntity<?> getProductByCategory(@PathVariable("id") Long id) {
		List<ProductForHomeDTO> productDTO = productService.findByCategorySub(id);
		if (Objects.isNull(productDTO)) {
			throw new ResourceNotFoundException("Not Found Products With CategorySubId = " + id + " !!!");
		}
		return ResponseEntity.ok(productDTO);
	}
	
	@GetMapping("/product/findbyname")
	public ResponseEntity<?> getProductByName(@RequestParam("name") String name) {
		List<ProductForHomeDTO> productDTO = productService.findByName(name);
		if (Objects.isNull(productDTO)) {
			return null;
		}
		return ResponseEntity.ok(productDTO);
	}
	
	@PostMapping("/product")
	public ResponseEntity<?> addProduct(//@RequestBody ProductDTO productDTO,
			@RequestParam("request") String json,
			@RequestParam("origin_picture") MultipartFile originPicture,
			@RequestParam("detail_picture") MultipartFile[] detailPicture) {
		ObjectMapper mapper = new ObjectMapper();
		ProductDTO productDTO = new ProductDTO();
		try {
			productDTO = mapper.readValue(json, ProductDTO.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// get url origin picture
		try {
			File fileResponse = googleDriveService.uploadGoogleFile(originPicture, originPicture.getContentType(),
					originPicture.getOriginalFilename());
			String href = fileResponse.getWebContentLink().replace("&export=download", "");
			productDTO.setPicture(href);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// get url detail pictures
		List<String> urlDetailPicture = new ArrayList<>();
		try {
			for (MultipartFile file : detailPicture) {
				File fileResponse = googleDriveService.uploadGoogleFile(file, file.getContentType(),
						file.getOriginalFilename());
				String href = fileResponse.getWebContentLink().replace("&export=download", "");
				urlDetailPicture.add(href);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		productDTO.setPictureDetails(urlDetailPicture);
		try {
			productService.create(productDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
}
