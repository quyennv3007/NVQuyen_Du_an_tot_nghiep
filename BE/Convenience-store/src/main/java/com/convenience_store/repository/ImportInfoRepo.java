package com.convenience_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.convenience_store.entity.ImportInfo;

public interface ImportInfoRepo extends JpaRepository<ImportInfo, Long> {
	
	@Query(value = "SELECT * FROM importInfo WHERE status = ?1 AND productID = ?2", nativeQuery = true)
	ImportInfo findByStatusAndProductID(Integer status, Long id);
	
	@Query(value = "SELECT * FROM importInfo WHERE importID = ?", nativeQuery = true)
	List<ImportInfo> findByImportID(String importID);
	
	@Query(value = "SELECT TOP 1 * FROM importInfo WHERE productID = ? Order By id desc", nativeQuery = true)
	ImportInfo findByProductID(Long id);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE importInfo SET status = ?1  WHERE id = ?2", nativeQuery = true)
	void updateStatus(Long status, Long id);
}
