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
public class CityTable {

	@Id
	private Integer cityId;

	private String cityName;

	@ManyToOne
	@JoinColumn(name = "state_id")
	private StateTable state;

}
