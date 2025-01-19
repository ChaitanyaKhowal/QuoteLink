package com.khowal.dto;

import lombok.Data;

@Data
public class UserDTO {

	private Integer id;
	private String name;
	private String email;
	private String phoneNo;
	private String password;
	private String updatedPassword;
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;

}
