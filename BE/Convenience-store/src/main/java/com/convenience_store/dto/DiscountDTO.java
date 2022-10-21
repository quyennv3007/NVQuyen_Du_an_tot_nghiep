package com.convenience_store.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Timestamp startDate;
	private Timestamp endDate;
	private Double percentage;
	private Long productID;
}
