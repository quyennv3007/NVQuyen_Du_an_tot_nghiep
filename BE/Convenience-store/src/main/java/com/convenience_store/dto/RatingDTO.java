package com.convenience_store.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.convenience_store.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Timestamp date;
	private String comment;
	private Integer star;
	private UserDTO user;
	private Long productId;
}
