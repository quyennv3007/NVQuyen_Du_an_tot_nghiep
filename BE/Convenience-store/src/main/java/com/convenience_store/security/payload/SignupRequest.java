package com.convenience_store.security.payload;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	private String password;
	@NotBlank
	private String name;
	private String email;
	@NotBlank
	private String phone;

}
