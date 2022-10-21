package com.convenience_store.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberShipDto implements Serializable {

	private static final long serialVersionUID = 4214228441499167635L;

	private Long id;
	private String type;
}
