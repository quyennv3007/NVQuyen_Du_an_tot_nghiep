package com.convenience_store.dto.admin;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportDTO_Admin implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private Timestamp date;
	private String address;
	private String shipperName;
	private String staffName;
	private String picture;
	private List<ImportInfoDTO_Admin> importInfo;
}
