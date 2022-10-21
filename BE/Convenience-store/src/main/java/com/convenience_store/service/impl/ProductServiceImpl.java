package com.convenience_store.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.dto.DiscountDTO;
import com.convenience_store.dto.PictureDetailsDTO;
import com.convenience_store.dto.ProductDTO;
import com.convenience_store.dto.ProductDetailsDTO;
import com.convenience_store.dto.ProductForHomeDTO;
import com.convenience_store.dto.ProductInfoDTO;
import com.convenience_store.dto.RatingDTO;
import com.convenience_store.dto.admin.ImportInfoForProduct;
import com.convenience_store.dto.admin.ProductDTO_Admin;
import com.convenience_store.dto.admin.ProductForAdminDTO;
import com.convenience_store.entity.Category;
import com.convenience_store.entity.CategorySub;
import com.convenience_store.entity.Discount;
import com.convenience_store.entity.ImportInfo;
import com.convenience_store.entity.PictureDetails;
import com.convenience_store.entity.Product;
import com.convenience_store.entity.ProductDetails;
import com.convenience_store.entity.ProductInfo;
import com.convenience_store.entity.UnitType;
import com.convenience_store.repository.ProductRepo;
import com.convenience_store.service.CategoryService;
import com.convenience_store.service.CategorySubService;
import com.convenience_store.service.DiscountService;
import com.convenience_store.service.ImportInfoService;
import com.convenience_store.service.PictureDetailsService;
import com.convenience_store.service.ProductDetailsService;
import com.convenience_store.service.ProductInfoService;
import com.convenience_store.service.ProductService;
import com.convenience_store.service.RatingService;
import com.convenience_store.service.UnitTypeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo repo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ProductInfoService productInfoService;

	@Autowired
	private ProductDetailsService productDetailsService;

	@Autowired
	private ImportInfoService importInfoService;

	@Autowired
	private DiscountService discountService;

	@Autowired
	private PictureDetailsService pictureDetailsService;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private ObjectMapper objMapper;
	@Autowired
	private UnitTypeService unitTypeService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CategorySubService categorySubService;
	
	@Transactional
	@Override
	public List<ProductForHomeDTO> findAll() {
		List<ProductDetails> productDetails = productDetailsService.findAll(Boolean.FALSE, Boolean.TRUE);
		if (productDetails == null) {
			return null;
		}
		List<ProductForHomeDTO> productForHomeDTO = new ArrayList<>();
		for (ProductDetails item : productDetails) {
			Product productTemp = repo.findByIdAndIsDeleted(item.getProduct().getId(), Boolean.FALSE);
			if (Objects.isNull(productTemp)) {
				continue;
			}
			ProductForHomeDTO productHomeTemp = new ProductForHomeDTO();
			productHomeTemp.setId(productTemp.getId());
			productHomeTemp.setDescription(productTemp.getDescription());
			productHomeTemp.setName(productTemp.getName());
			productHomeTemp.setPicture(productTemp.getPicture());
			productHomeTemp.setUnitType(productTemp.getUnitType());
			productHomeTemp.setCategorySub(productTemp.getCategorySub());
			// set StarAverage
			List<RatingDTO> ratingDTO = ratingService.findByProduct(item.getProduct());
			if (ratingDTO != null && !ratingDTO.isEmpty()) {
				// format number
				Double starAveraged = Double.parseDouble(String.format("%.2f",
						ratingDTO.stream().mapToDouble(RatingDTO::getStar).average().orElse(Double.NaN)));
				productHomeTemp.setStarAveraged(starAveraged);
			}
			// set ImportInfo for productDetails
			try {
				ImportInfo importInfo = importInfoService.findByStatusAndProductID(1, productTemp.getId());
				if (importInfo != null) {
					productDetailsService.updateImportInfo(importInfo.getId(), productTemp.getId());
					item.setImportInfo(importInfo);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			// set ProductDetailsDTO for ProductDTO
			ProductDetailsDTO productDetailsDTO = mapper.map(item, ProductDetailsDTO.class);
			productHomeTemp.setProductDetailsDTO(productDetailsDTO);
			// set DiscountDTO for ProductDTO
			DiscountDTO discountDTO = discountService.findByProductId(item.getProduct().getId());
			if (!Objects.isNull(discountDTO)) {
				productHomeTemp.setDiscountDTO(discountDTO);
			}
			productForHomeDTO.add(productHomeTemp);

		}
		if (productForHomeDTO.isEmpty()) {
			return null;
		}
		return productForHomeDTO;
	}

	@Transactional
	@Override
	public List<ProductForAdminDTO> findAll_admin() {
		List<ProductDetails> productDetails = productDetailsService.findAll_admin(Boolean.FALSE);
		if (productDetails == null) {
			return null;
		}
		List<ProductForAdminDTO> productForHomeDTO = new ArrayList<>();
		for (ProductDetails item : productDetails) {
			Product productTemp = repo.findByIdAndIsDeleted(item.getProduct().getId(), Boolean.FALSE);
			if (Objects.isNull(productTemp)) {
				continue;
			}
			ProductForAdminDTO productHomeTemp = new ProductForAdminDTO();
			productHomeTemp.setId(productTemp.getId());
			productHomeTemp.setDescription(productTemp.getDescription());
			productHomeTemp.setName(productTemp.getName());
			productHomeTemp.setPicture(productTemp.getPicture());
			productHomeTemp.setUnitType(productTemp.getUnitType());
			productHomeTemp.setCategorySub(productTemp.getCategorySub());
			// set StarAverage
			List<RatingDTO> ratingDTO = ratingService.findByProduct(item.getProduct());
			if (ratingDTO != null && !ratingDTO.isEmpty()) {
				// format number
				Double starAveraged = Double.parseDouble(String.format("%.2f",
						ratingDTO.stream().mapToDouble(RatingDTO::getStar).average().orElse(Double.NaN)));
				productHomeTemp.setStarAveraged(starAveraged);
			}
			// set ImportInfo for productDetails
			try {
				ImportInfo importInfo = importInfoService.findByStatusAndProductID(1, productTemp.getId());
				if (importInfo != null) {
					productDetailsService.updateImportInfo(importInfo.getId(), productTemp.getId());
					item.setImportInfo(importInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// set ProductDetailsDTO for ProductDTO
			ProductDetailsDTO productDetailsDTO = mapper.map(item, ProductDetailsDTO.class);
			productHomeTemp.setProductDetailsDTO(productDetailsDTO);
			// set DiscountDTO for ProductDTO
			DiscountDTO discountDTO = discountService.findByProductId(item.getProduct().getId());
			if (!Objects.isNull(discountDTO)) {
				productHomeTemp.setDiscountDTO(discountDTO);
			}
			// set importInfo for ProductDTO
			ImportInfo importInfo = importInfoService.findByProductID(item.getProduct().getId());
			if (!Objects.isNull(importInfo)) {
				productHomeTemp.setImportInfo(mapper.map(importInfo, ImportInfoForProduct.class));
			}
			try {
				productForHomeDTO.add(productHomeTemp);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (productForHomeDTO.isEmpty()) {
			return null;
		}
		return productForHomeDTO;
	}

	@Override
	public ProductDTO findById(Long id) {
		Optional<Product> product = repo.findById(id);
		if (product.isEmpty()) {
			return null;
		}
		ProductDTO productDTO = new ProductDTO();
		if (product.isPresent()) {
			productDTO = mapper.map(product.get(), ProductDTO.class);
		}
		// set ProductDetailsDTO for ProductDTO
		ProductDetails productDetails = productDetailsService.findByProduct(product.get());
		if (!Objects.isNull(productDetails)) {
			ProductDetailsDTO productDetailsDTO = mapper.map(productDetails, ProductDetailsDTO.class);
			productDTO.setProductDetailsDTO(productDetailsDTO);
		}
		// set ProductInfoDTO for ProductDTO
		ProductInfo productInfo = productInfoService.findByProduct(product.get());
		if (!Objects.isNull(productInfo)) {
			ProductInfoDTO productInfoDTO = mapper.map(productInfo, ProductInfoDTO.class);
			productDTO.setProductInfoDTO(productInfoDTO);
		}
		// set ratingDTO for productDTO
		List<RatingDTO> ratingDTO = ratingService.findByProduct(product.get());
		if (ratingDTO != null && !ratingDTO.isEmpty()) {
			productDTO.setRatingDTO(ratingDTO);
		}
		// set DiscountDTO for ProductDTO
		DiscountDTO discountDTO = discountService.findByProductId(product.get().getId());
		if (!Objects.isNull(discountDTO)) {
			productDTO.setDiscountDTO(discountDTO);
		}
		// set picture for productDTO
		List<PictureDetailsDTO> pictureDetailsDTO = pictureDetailsService.findAll(product.get(), Boolean.FALSE);
		List<String> picture = new ArrayList<>();
		for (PictureDetailsDTO itemPicture : pictureDetailsDTO) {
			String pictureTemp = itemPicture.getName();
			picture.add(pictureTemp);
		}
		productDTO.setPictureDetails(picture);
		return productDTO;
	}

	@Override
	public Product updateAdmin(ProductDTO_Admin productDTO, Long id) {
		// delete all previous images
		pictureDetailsService.deleteByProductID(id);
		// resetting unit category and categorySub
		Category category = categoryService.findById(productDTO.getCategorySub().getCategory().getId());
		UnitType unitType = unitTypeService.findById(productDTO.getUnitType().getId());
		CategorySub cateSub = categorySubService.findById(productDTO.getCategorySub().getId());
//		Product product = mapper.map(productDTO, Product.class);
//		product = mapper.map(productDTO, Product.class);
		Product product = repo.findByIdAndIsDeleted(id, false);
		product = mapper.map(productDTO, Product.class);
		product.setIsDeleted(false);
		product.setCategorySub(cateSub);
		product.setUnitType(unitType);
		product = repo.saveAndFlush(product);
		// save productInfo
		List<ProductInfo> productInfo = objMapper.convertValue(productDTO.getProductInfo(),
				new TypeReference<List<ProductInfo>>() {
				});
		System.out.println("runn1");
		for (ProductInfo item : productInfo) {
			item.setProduct(product);
		}
		productInfo = productInfoService.saveAll(productInfo);

		// save discount
		List<Discount> discount = objMapper.convertValue(productDTO.getDiscount(), new TypeReference<List<Discount>>() {
		});
		for (Discount item : discount) {
			item.setProduct(product);
		}
		discount = discountService.saveAll(discount);
		System.out.println("runn2");
		// save productDetails
		List<ProductDetails> productDetails = objMapper.convertValue(productDTO.getDetailedProduct(),
				new TypeReference<List<ProductDetails>>() {
				});
		for (ProductDetails item : productDetails) {
			item.setProduct(product);
			item.setIsDeleted(false);
		}
		productDetails = productDetailsService.saveAll(productDetails);
		System.out.println("runn3");
		// save PictureDetails
		List<PictureDetails> pictureDetails = objMapper.convertValue(productDTO.getDetailedPicture(),
				new TypeReference<List<PictureDetails>>() {
				});
		for (PictureDetails item : pictureDetails) {
			item.setProduct(product);
		}
		pictureDetails = pictureDetailsService.saveAll(pictureDetails);
		System.out.println("runn4");
		return product;
	}

	@Override
	public Product createAdmin(ProductDTO_Admin productDTO) {
		// resetting unit category and categorySub
		Category category = categoryService.findById(productDTO.getCategorySub().getCategory().getId());
		UnitType unitType = unitTypeService.findById(productDTO.getUnitType().getId());
		CategorySub cateSub = categorySubService.findById(productDTO.getCategorySub().getId());
		// save product
		Product product = mapper.map(productDTO, Product.class);
		product.setCategorySub(cateSub);
		product.setUnitType(unitType);
		product.setIsDeleted(false);
		product = repo.saveAndFlush(product);

		// save productInfo
		List<ProductInfo> productInfo = objMapper.convertValue(productDTO.getProductInfo(),
				new TypeReference<List<ProductInfo>>() {
				});
		for (ProductInfo item : productInfo) {
			item.setProduct(product);
		}
		productInfo = productInfoService.saveAll(productInfo);

		// save discount
		List<Discount> discount = objMapper.convertValue(productDTO.getDiscount(), new TypeReference<List<Discount>>() {
		});
		for (Discount item : discount) {
			item.setProduct(product);
		}
		discount = discountService.saveAll(discount);

		// save productDetails
		List<ProductDetails> productDetails = objMapper.convertValue(productDTO.getDetailedProduct(),
				new TypeReference<List<ProductDetails>>() {
				});
		for (ProductDetails item : productDetails) {
			item.setProduct(product);
			item.setIsDeleted(false);
		}
		productDetails = productDetailsService.saveAll(productDetails);

		// save PictureDetails
		List<PictureDetails> pictureDetails = objMapper.convertValue(productDTO.getDetailedPicture(),
				new TypeReference<List<PictureDetails>>() {
				});
		for (PictureDetails item : pictureDetails) {
			item.setProduct(product);
		}
		pictureDetails = pictureDetailsService.saveAll(pictureDetails);
		return product;
	}

	@Transactional
	@Override
	public Product create(ProductDTO productDTO) {

		// save product
		Product product = mapper.map(productDTO, Product.class);
		product.setIsDeleted(Boolean.FALSE);
		product = repo.saveAndFlush(product);
		// save productInfo
		ProductInfo productInfo = mapper.map(productDTO.getProductInfoDTO(), ProductInfo.class);
		productInfo.setProduct(product);
		productInfo = productInfoService.save(productInfo);
		// save productDetails
		productDTO.getProductDetailsDTO().setDateEnd(new Timestamp(0));
		productDTO.getProductDetailsDTO().setDateRelease(new Timestamp(0));
		productDTO.getProductDetailsDTO().setPriceSell(0D);
		productDTO.getProductDetailsDTO().setQuantity(0);
		productDTO.getProductDetailsDTO().setAvailable(Boolean.FALSE);
		ProductDetails productDetails = mapper.map(productDTO.getProductDetailsDTO(), ProductDetails.class);
		productDetails.setProduct(product);
		productDetails = productDetailsService.save(productDetails);
		// save PictureDetails
		List<PictureDetails> list = new ArrayList<>();
		for (String picture : productDTO.getPictureDetails()) {
			PictureDetails pictureDetails = new PictureDetails();
			pictureDetails.setProduct(product);
			pictureDetails.setName(picture);
			list.add(pictureDetails);
			pictureDetailsService.save(pictureDetails);
		}
		return product;
	}

	@Transactional
	@Override
	public void delete(Long id) {
		repo.deleteLogical(id);
	}

	@Override
	public Product findProduct(Long id) {
		Optional<Product> product = repo.findById(id);
		if (product.isPresent()) {
			return product.get();
		}
		return null;
	}

	@Transactional
	@Override
	public List<ProductForHomeDTO> findByCategorySub(Long id) {
		List<ProductDetails> productDetails = productDetailsService.findAll(Boolean.FALSE, Boolean.TRUE);
		if (productDetails == null) {
			return null;
		}
		List<ProductForHomeDTO> productForHomeDTO = new ArrayList<>();
		for (ProductDetails item : productDetails) {

			Product productTemp = repo.findByIdAndIsDeleted(item.getProduct().getId(), Boolean.FALSE);
			if (Objects.isNull(productTemp)) {
				continue;
			}
			if (productTemp.getCategorySub().getId() == id) {
				ProductForHomeDTO productHomeTemp = new ProductForHomeDTO();
				productHomeTemp.setId(productTemp.getId());
				productHomeTemp.setDescription(productTemp.getDescription());
				productHomeTemp.setName(productTemp.getName());
				productHomeTemp.setPicture(productTemp.getPicture());
				productHomeTemp.setUnitType(productTemp.getUnitType());
				productHomeTemp.setCategorySub(productTemp.getCategorySub());
				// set StarAverage
				List<RatingDTO> ratingDTO = ratingService.findByProduct(item.getProduct());
				if (ratingDTO != null && !ratingDTO.isEmpty()) {
					// format number
					Double starAveraged = Double.parseDouble(String.format("%.2f",
							ratingDTO.stream().mapToDouble(RatingDTO::getStar).average().orElse(Double.NaN)));
					productHomeTemp.setStarAveraged(starAveraged);
				}
				// set ImportInfo for productDetails
//				ImportInfo importInfo = importInfoService.findByStatusAndProductID(1, productTemp.getId());
//				productDetailsService.updateImportInfo(importInfo.getId(), productTemp.getId());
//				item.setImportInfo(importInfo);
				try {
					ImportInfo importInfo = importInfoService.findByStatusAndProductID(1, productTemp.getId());
					if(importInfo != null) {
						productDetailsService.updateImportInfo(importInfo.getId(), productTemp.getId());
						item.setImportInfo(importInfo);				
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				// set ProductDetailsDTO for ProductDTO
				ProductDetailsDTO productDetailsDTO = mapper.map(item, ProductDetailsDTO.class);
				productHomeTemp.setProductDetailsDTO(productDetailsDTO);
				// set DiscountDTO for ProductDTO
				DiscountDTO discountDTO = discountService.findByProductId(item.getProduct().getId());
				if (!Objects.isNull(discountDTO)) {
					productHomeTemp.setDiscountDTO(discountDTO);
				}
				productForHomeDTO.add(productHomeTemp);
			}
		}
		if (productForHomeDTO.isEmpty()) {
			return null;
		}
		return productForHomeDTO;
	}

	@Override
	public List<ProductForHomeDTO> findByName(String name) {
		List<ProductForHomeDTO> productForHomeDTO = new ArrayList<>();
		List<Product> product = repo.findByName("%" + name.trim() + "%");
		if (product == null) {
			return null;
		}
		for (Product productTemp : product) {
			ProductForHomeDTO productHomeTemp = new ProductForHomeDTO();
			productHomeTemp.setId(productTemp.getId());
			productHomeTemp.setDescription(productTemp.getDescription());
			productHomeTemp.setName(productTemp.getName());
			productHomeTemp.setPicture(productTemp.getPicture());
			productHomeTemp.setUnitType(productTemp.getUnitType());
			productHomeTemp.setCategorySub(productTemp.getCategorySub());
			// set StarAverage
			List<RatingDTO> ratingDTO = ratingService.findByProduct(productTemp);
			if (ratingDTO != null && !ratingDTO.isEmpty()) {
				// format number
				Double starAveraged = Double.parseDouble(String.format("%.2f",
						ratingDTO.stream().mapToDouble(RatingDTO::getStar).average().orElse(Double.NaN)));
				productHomeTemp.setStarAveraged(starAveraged);
			}
			// set ProductDetailsDTO for ProductDTO
			ProductDetails productDetails = productDetailsService.findByProduct(productTemp);
			ProductDetailsDTO productDetailsDTO = mapper.map(productDetails, ProductDetailsDTO.class);
			productHomeTemp.setProductDetailsDTO(productDetailsDTO);
			// set DiscountDTO for ProductDTO
			DiscountDTO discountDTO = discountService.findByProductId(productTemp.getId());
			if (!Objects.isNull(discountDTO)) {
				productHomeTemp.setDiscountDTO(discountDTO);
			}
			productForHomeDTO.add(productHomeTemp);
		}

		if (productForHomeDTO.isEmpty()) {
			return null;
		}
		return productForHomeDTO;
	}

	@Override
	public Product findById_Admin(Long id) {
		return repo.findById(id).get();
	}

	@Override
	public void forbidden(Product product) {
		repo.save(product);
		
	}

	@Override
	public Product findByIdToDelete(Long id) {
		return repo.findById(id).get();
	}

	@Override
	public void delete(Product product) {
		repo.saveAndFlush(product);
	}

	@Override
	public List<ProductForAdminDTO> findAll_Deleted_admin() {
		List<ProductDetails> productDetails = productDetailsService.findAll_admin(Boolean.FALSE);
		if (productDetails == null) {
			return null;
		}
		List<ProductForAdminDTO> productForHomeDTO = new ArrayList<>();
		for (ProductDetails item : productDetails) {
			Product productTemp = repo.findByIdAndIsDeleted(item.getProduct().getId(), Boolean.TRUE);
			if (Objects.isNull(productTemp)) {
				continue;
			}
			ProductForAdminDTO productHomeTemp = new ProductForAdminDTO();
			productHomeTemp.setId(productTemp.getId());
			productHomeTemp.setDescription(productTemp.getDescription());
			productHomeTemp.setName(productTemp.getName());
			productHomeTemp.setPicture(productTemp.getPicture());
			productHomeTemp.setUnitType(productTemp.getUnitType());
			productHomeTemp.setCategorySub(productTemp.getCategorySub());
			// set StarAverage
			List<RatingDTO> ratingDTO = ratingService.findByProduct(item.getProduct());
			if (ratingDTO != null && !ratingDTO.isEmpty()) {
				// format number
				Double starAveraged = Double.parseDouble(String.format("%.2f",
						ratingDTO.stream().mapToDouble(RatingDTO::getStar).average().orElse(Double.NaN)));
				productHomeTemp.setStarAveraged(starAveraged);
			}
			// set ImportInfo for productDetails
			try {
				ImportInfo importInfo = importInfoService.findByStatusAndProductID(1, productTemp.getId());
				if (importInfo != null) {
					productDetailsService.updateImportInfo(importInfo.getId(), productTemp.getId());
					item.setImportInfo(importInfo);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			// set ProductDetailsDTO for ProductDTO
			ProductDetailsDTO productDetailsDTO = mapper.map(item, ProductDetailsDTO.class);
			productHomeTemp.setProductDetailsDTO(productDetailsDTO);
			// set DiscountDTO for ProductDTO
			DiscountDTO discountDTO = discountService.findByProductId(item.getProduct().getId());
			if (!Objects.isNull(discountDTO)) {
				productHomeTemp.setDiscountDTO(discountDTO);
			}
			// set importInfo for ProductDTO
			ImportInfo importInfo = importInfoService.findByProductID(item.getProduct().getId());
			if (!Objects.isNull(importInfo)) {
				productHomeTemp.setImportInfo(mapper.map(importInfo, ImportInfoForProduct.class));
			}
			try {
				productForHomeDTO.add(productHomeTemp);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (productForHomeDTO.isEmpty()) {
			return null;
		}
		return productForHomeDTO;
	}

	@Override
	public void recover(Product product) {
		repo.saveAndFlush(product);
		
	}

	@Override
	public List<Object[]> getPopularProduct() {
		return repo.getPopularProduct();
	}

}
