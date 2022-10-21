package com.convenience_store.security.payload;

import java.util.List;

import com.convenience_store.dto.MemberShipDto;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String phone;
	private String name;
	private String email;
	private String address;
	private Boolean gender;
	private MemberShipDto memberShip;
	private List<String> roles;
	
	public JwtResponse(String token, Long id, String phone, String name, String email, String address, Boolean gender,
			MemberShipDto memberShip, List<String> roles) {
		super();
		this.token = token;
		this.id = id;
		this.phone = phone;
		this.name = name;
		this.email = email;
		this.address = address;
		this.gender = gender;
		this.memberShip = memberShip;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public MemberShipDto getMemberShip() {
		return memberShip;
	}

	public void setMemberShip(MemberShipDto memberShip) {
		this.memberShip = memberShip;
	}

	public List<String> getRoles() {
		return roles;
	}
	
}
