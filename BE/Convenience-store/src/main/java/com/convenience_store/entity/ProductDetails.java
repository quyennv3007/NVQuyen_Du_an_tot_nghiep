package com.convenience_store.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detailedProduct")
public class ProductDetails implements Serializable {
	
	private static final long serialVersionUID = -3020777698965286403L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "priceSell")
	private Double priceSell;
	
	@Column(name = "dateRelease")
	private Timestamp dateRelease;
	
	@Column(name = "dateEnd")
	private Timestamp dateEnd;
	
	@Column(name = "available")
	private Boolean available;
	
	@Column(name = "isDeleted")
	private Boolean isDeleted;
	
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "productID", referencedColumnName = "id")
	private Product product;
	
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "importInfoID", referencedColumnName = "id")
	private ImportInfo importInfo;
}
