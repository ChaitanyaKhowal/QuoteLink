package com.khowal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khowal.entity.CityTable;

public interface CityRepo extends JpaRepository<CityTable, Integer> {

	public List<CityTable> findByStateStateId(Integer stateId);

}
