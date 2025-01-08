package com.khowal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StateTable {

	@Id
	private Integer stateId;

	private String stateName;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private CountryTable country;

}
