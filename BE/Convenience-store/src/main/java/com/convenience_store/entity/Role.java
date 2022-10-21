package com.convenience_store.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role implements Serializable {
	
	private static final long serialVersionUID = 8158264130198788126L;

	@Id
	private String id;
	
	@Column(name = "name")
	private String name;
}
