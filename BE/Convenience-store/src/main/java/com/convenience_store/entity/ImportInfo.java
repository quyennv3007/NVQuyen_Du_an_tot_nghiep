package com.convenience_store.entity;

import java.io.Serializable;

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
@Table(name = "importInfo")
public class ImportInfo implements Serializable {
	
	private static final long serialVersionUID = -2036593879032217408L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "priceImport")
	private Double priceImport;
	
	@Column(name = "quantityImport")
	private Integer quantityImport;
	
	@Column(name = "status")
	private Long status;
	
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "productID", referencedColumnName = "id")
	private Product product;
	
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "importID", referencedColumnName = "id")
	private Import importID;
	
}
