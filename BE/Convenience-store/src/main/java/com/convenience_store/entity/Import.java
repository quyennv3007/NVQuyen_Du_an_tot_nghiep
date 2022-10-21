package com.convenience_store.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "import")
public class Import implements Serializable {
	
	private static final long serialVersionUID = 5633124476883973354L;

	@Id
	private String id;
	
	@Column(name = "date")
	@CreationTimestamp
	private Timestamp date;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "shipperName")
	private String shipperName;
	
	@Column(name = "staffName")
	private String staffName;
	
	@Column(name = "picture")
	private String picture;
	
	@Column(name = "isDeleted")
	private Boolean isDeleted;
}
