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
@Table(name = "product")
public class Product implements Serializable {
	
	private static final long serialVersionUID = -7426932349764109461L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "picture")
	private String picture;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "brand")
	private String brand;
	
	@Column(name = "origin")
	private String origin;
	
	@Column(name = "isDeleted")
	private Boolean isDeleted;
	
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "categorySubID", referencedColumnName = "id")
	private CategorySub categorySub;
	
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "typeID", referencedColumnName = "id")
	private UnitType unitType;
	
	public CategorySub getCategorySub() {
		return new CategorySub(categorySub.getId(), categorySub.getName(), categorySub.getCategory());
	}
	
	public UnitType getUnitType() {
		return new UnitType(unitType.getId(), unitType.getType());
	}
}
