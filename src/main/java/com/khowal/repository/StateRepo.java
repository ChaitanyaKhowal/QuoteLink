package com.khowal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khowal.entity.StateTable;

public interface StateRepo extends JpaRepository<StateTable, Integer> {

	public List<StateTable> findByCountryCountryId(Integer countryId);
	
}
