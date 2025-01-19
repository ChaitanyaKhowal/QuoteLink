package com.khowal.service;

import java.util.Map;

import com.khowal.dto.PasswordDTO;
import com.khowal.dto.QuoteResponseDTO;
import com.khowal.dto.UserDTO;

public interface UserService {

	public UserDTO login(String email, String password);

	public Map<Integer, String> getCountries();

	public Map<Integer, String> getStates(Integer countryId);

	public Map<Integer, String> getCities(Integer stateId);
	
	public boolean isEmailUnique(String email);
	
	public boolean register(UserDTO userDto);
	
	public boolean resetPassword(PasswordDTO passwordDto);
	
	public QuoteResponseDTO getQuotation();
	

}