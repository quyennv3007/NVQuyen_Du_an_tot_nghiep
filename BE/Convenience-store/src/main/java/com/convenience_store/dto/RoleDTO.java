package com.convenience_store.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
}
