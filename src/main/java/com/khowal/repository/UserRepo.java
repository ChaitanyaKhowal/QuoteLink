package com.khowal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khowal.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Integer> {

	public UserEntity findByEmailAndPassword(String email, String password);

	public UserEntity findByEmail(String email);

}
