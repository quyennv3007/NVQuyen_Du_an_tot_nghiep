package com.convenience_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.convenience_store.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
	Product findByIdAndIsDeleted(Long id, Boolean isDeleted);
	
	@Query(value = "SELECT * FROM product WHERE categorySubID = ?", nativeQuery = true)
	List<Product> findByCategorySub(Long id);
	
//	@Query(value = "SELECT * FROM product WHERE name LIKE %?% and isDeleted = 0", nativeQuery = true)
//	List<Product> findByName(String name);
	
	@Query("Select p from Product p where p.name Like?1 and p.isDeleted = 0")
	List<Product>findByName(String name);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE product SET isDeleted = 1 WHERE id = ?", nativeQuery = true)
	void deleteLogical(Long id);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT INTO product VALUES( name = ?1 , "
			+ "picture = ?2, description = ?3, brand = ?4, origin = ?5, "
			+ "isDeleted = ?6, categorySubID = ?7, typeID = ?8 )", nativeQuery = true)
	void insert(String name, String picture, String description, String brand, String origin, Boolean isDeleted, Long categorySubID, Long typeID);

	
	@Query(value="Select top 10 p.id ,p.name,p.picture,sum(dti.quantity) as total "
			+ "from invoice i "
			+ "full join detailedInvoice dti on i.id = dti.invoiceID "
			+ "full join detailedProduct dtp on dtp.id = dti.detailedProductID "
			+ "full join product p on p.id = dtp.productID "
			+ "where i.status = N'Paid' "
			+ "group by p.id ,p.name,p.picture "
			+ "order by total desc",nativeQuery=true)
	List<Object[]> getPopularProduct();
}
