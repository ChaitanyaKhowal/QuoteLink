package com.khowal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CountryTable {

	@Id
	private Integer countryId;

	private String countryName;

}
