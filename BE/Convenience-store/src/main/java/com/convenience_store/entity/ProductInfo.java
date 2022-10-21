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
@Table(name = "productInfo")
public class ProductInfo implements Serializable {
	
	private static final long serialVersionUID = -1321143216685408220L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "weight")
	private String weight;
	
	@Column(name = "guide")
	private String guide;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "component")
	private String component;
	
	@Column(name = "capacity")
	private String capacity;
	
	@Column(name = "preservation")
	private String usage;
	
	@Column(name = "others")
	private String others;
	
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "productID", referencedColumnName = "id")
	private Product product;
}
