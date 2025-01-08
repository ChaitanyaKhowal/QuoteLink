package com.khowal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khowal.dto.UserDTO;
import com.khowal.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Integer> {

	public UserDTO findByEmailAndPassword(String email, String password);
	
	public UserDTO findByEmail(String email);
	
	public UserDTO findByPassword(String password);
	
	
}
