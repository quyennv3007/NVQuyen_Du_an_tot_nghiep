package com.convenience_store.dto;

import java.io.Serializable;

import com.convenience_store.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Role role;
}
