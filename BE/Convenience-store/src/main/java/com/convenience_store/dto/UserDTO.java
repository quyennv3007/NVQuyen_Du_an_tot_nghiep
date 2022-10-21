package com.convenience_store.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

	private static final long serialVersionUID = -6324251952619540157L;

	private Long id;
	private String name;
	private String email;
	private String phone;
	private String address;
	private Boolean gender;
	private String photo;
	private Boolean isDeleted;
	private List<AuthorityDTO> authorityDTO;
	private MemberShipDto memberShip;
	
	public UserDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}
