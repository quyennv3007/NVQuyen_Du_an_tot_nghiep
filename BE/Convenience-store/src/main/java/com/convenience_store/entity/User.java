package com.convenience_store.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.convenience_store.dto.MemberShipDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
	
	private static final long serialVersionUID = -175273394017984259L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "gender")
	private Boolean gender;
	
	@Column(name = "photo")
	private String photo;
	
	@Column(name = "isDeleted")
	private Boolean isDeleted;
	
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "memberShipID", referencedColumnName = "id")
	private MemberShip memberShip;
	
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	List<Authority> authorities;
	
	public MemberShipDto getMemberShip() {
		return new MemberShipDto(memberShip.getId(), memberShip.getType());
	}
	
}
