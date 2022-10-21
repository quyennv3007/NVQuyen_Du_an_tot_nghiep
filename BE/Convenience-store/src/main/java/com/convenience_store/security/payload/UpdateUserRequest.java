package com.convenience_store.security.payload;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String phone;
	private String name;
	private String email;
	private String address;
	private Boolean gender;
	private String photo;
}
