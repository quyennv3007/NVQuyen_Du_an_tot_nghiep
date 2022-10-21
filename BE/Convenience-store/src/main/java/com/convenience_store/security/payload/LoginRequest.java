package com.convenience_store.security.payload;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest {
	@NotBlank
	private String username;
	@NotBlank(message = "Password must not null")
	private String password;
}
