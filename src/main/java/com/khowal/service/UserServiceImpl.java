package com.khowal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khowal.dto.PasswordDTO;
import com.khowal.dto.QuoteResponseDTO;
import com.khowal.dto.UserDTO;
import com.khowal.entity.CityTable;
import com.khowal.entity.CountryTable;
import com.khowal.entity.StateTable;
import com.khowal.entity.UserEntity;
import com.khowal.repository.CityRepo;
import com.khowal.repository.CountryRepo;
import com.khowal.repository.StateRepo;
import com.khowal.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CountryRepo countryRepo;

	@Autowired
	private StateRepo stateRepo;

	@Autowired
	private CityRepo cityRepo;

	@Override
	public UserDTO login(UserDTO dto) {
		UserDTO byEmailAndPassword = userRepo.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
		if (byEmailAndPassword != null) {
			return byEmailAndPassword;
		} else {
			return null;
		}
	}

	@Override
	public Map<Integer, String> getCountries() {
		Map<Integer, String> countriesMap = new HashMap<>();
		List<CountryTable> countries = countryRepo.findAll();
		for (CountryTable countryTable : countries) {
			countriesMap.put(countryTable.getCountryId(), countryTable.getCountryName());
		}
		return countriesMap;
	}

	@Override
	public Map<Integer, String> getStatesByCountry(String countryName) {
		Map<Integer, String> statesMap = new HashMap<>();
		CountryTable country = countryRepo.findByCountry(countryName);
		if (country != null) {
			List<StateTable> states = stateRepo.findByCountry(country);
			for (StateTable state : states) {
				statesMap.put(state.getStateId(), state.getStateName());
			}
		}
		return statesMap;
	}

	@Override
	public Map<Integer, String> getCitiesByState(String stateName) {
		Map<Integer, String> citiesMap = new HashMap<>();
		List<CityTable> cities = cityRepo.findAll();
		for (CityTable cityTable : cities) {
			citiesMap.put(cityTable.getCityId(), cityTable.getCityName());
		}
		return citiesMap;
	}

	@Override
	public boolean isEmailUnique(String email) {
		UserDTO byEmail = userRepo.findByEmail(email);
		return byEmail == null;
	}

	public String generateRandomPassword() {
//		String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$";
//		int passwordLength = 12;
//		SecureRandom secureRandom = new SecureRandom();
//		StringBuilder password = new StringBuilder();
//		for (int i = 0; i < passwordLength; i++) {
//			int nextInt = secureRandom.nextInt(allowedChars.length());
//			password.append(allowedChars.charAt(nextInt));
//		}
		return null;
	}

	@Override
	public boolean register(UserDTO userDto) {
		String random = generateRandomPassword();
		userDto.setPassword(random);
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDto, userEntity);
		UserEntity save = userRepo.save(userEntity);
		return save != null;
	}

	@Override
	public boolean resetPassword(PasswordDTO passwordDto) {
		return false;
	}

	@Override
	public QuoteResponseDTO getQuotation() {
		// TODO Auto-generated method stub
		return null;
	}

}
