package com.khowal.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String email;
	private String phoneNo;
	private String password;
	private String updatedPassword;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate createdOn;

	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDate updatedOn;

	@ManyToOne
	@JoinColumn(name = "city_id")
	private CityTable city;

	@ManyToOne
	@JoinColumn(name = "state_id")
	private StateTable state;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private CountryTable country;

}
