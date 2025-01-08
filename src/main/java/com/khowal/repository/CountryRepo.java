package com.khowal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khowal.dto.UserDTO;
import com.khowal.entity.CountryTable;

public interface CountryRepo extends JpaRepository<CountryTable, Integer> {

	public CountryTable findByCountry(String countryName);
	
}
