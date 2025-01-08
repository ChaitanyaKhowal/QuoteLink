package com.khowal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khowal.entity.CityTable;
import com.khowal.entity.StateTable;

public interface CityRepo extends JpaRepository<CityTable, Integer> {

	public List<CityTable> findByState(StateTable state);
	
}
