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

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {
	
	private static final long serialVersionUID = -5759289181313984287L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "date")
	@CreationTimestamp
	private Timestamp date;
	
	@Column(name = "customerNotes")
	private String customerNotes;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "totalPayment")
	private Double totalPayment;
	
	@Column(name = "totalPaymentNet")
	private Double totalPaymentNet;
	
	@Column(name = "status")
	private String status;
	
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "userID", referencedColumnName = "id")
	private User user;
}
