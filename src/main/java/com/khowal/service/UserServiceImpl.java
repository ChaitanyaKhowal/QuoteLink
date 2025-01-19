package com.khowal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

	@Autowired
	private EmailService emailService;

	@Override
	public UserDTO login(String email, String password) {
		UserEntity entity = userRepo.findByEmailAndPassword(email, password);
		if (entity != null) {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(entity, userDTO);
			return userDTO;
		}
		return null;
	}

	@Override
	public Map<Integer, String> getCountries() {
		List<CountryTable> list = countryRepo.findAll();
		Map<Integer, String> countryMap = new HashMap<>();
		list.forEach(country -> {
			countryMap.put(country.getCountryId(), country.getCountryName());
		});
		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		List<StateTable> list = stateRepo.findByCountryCountryId(countryId);
		Map<Integer, String> stateMap = new HashMap<>();
		list.forEach(states -> {
			stateMap.put(states.getStateId(), states.getStateName());
		});
		return stateMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		List<CityTable> list = cityRepo.findByStateStateId(stateId);
		Map<Integer, String> cityMap = new HashMap<>();
		list.forEach(city -> {
			cityMap.put(city.getCityId(), city.getCityName());
		});
		return cityMap;
	}

	@Override
	public boolean isEmailUnique(String email) {
		UserEntity byEmail = userRepo.findByEmail(email);
		return byEmail == null;
	}

	@Override
	public boolean register(UserDTO userDto) {
		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(userDto, entity);

		String randomPassword = generateRandomPassword();
		entity.setPassword(randomPassword);
		entity.setUpdatedPassword("NO");

		CountryTable country = countryRepo.findById(userDto.getCountryId()).get();
		entity.setCountry(country);

		StateTable state = stateRepo.findById(userDto.getStateId()).get();
		entity.setState(state);

		CityTable city = cityRepo.findById(userDto.getCityId()).get();
		entity.setCity(city);

		UserEntity saved = userRepo.save(entity);

		if (saved != null) {
			String subject = "Registration Saved Successfully";
			String body = "<html><body>" + "<h2 style='color: #4CAF50;'>Welcome to Our Service!</h2>"
					+ "<p>Dear <strong>" + userDto.getName() + "</strong>,</p>"
					+ "<p>Thank you for registering with us. We are excited to have you on board!</p>"
					+ "<p>Your registration was successful, and your password has been generated:</p>"
					+ "<div style='padding: 15px; background-color: #f4f4f4; border-radius: 5px; font-size: 18px; font-weight: bold; color: #333;'>"
					+ "<strong>" + randomPassword + "</strong>" + "</div>"
					+ "<p>If you did not request this registration, please ignore this email.</p>"
					+ "<p>For any questions or support, feel free to contact us.</p>" + "<br>"
					+ "<p style='font-size: 14px; color: #777;'>Best regards,<br>The <strong>[Your Company]</strong> Team</p>"
					+ "<p>For any inquiries, feel free to reach us at: <a href='mailto:chiragkhowal300@gmail.com'>chiragkhowal300@gmail.com</a></p>"
					+ "<br><hr>" + "<footer>" + "<div style='text-align: center; padding-top: 20px;'>"
					+ "<div class='footerContainer' style='padding-bottom: 10px;'>" + "</div>"
					+ "<div class='footerBottom' style='font-size: 14px; color: #777;'>"
					+ "<p>Thanks for visiting! Feel free to connect with me.</p>"
					+ "<p>Designer |<span style='font-weight: bold;'> Chaitanya Khowal</span></p>"
					+ "<p>Copyright &copy;2025;</p>" + "</div>" + "</div>" + "</footer>" + "</body></html>";

			emailService.sendEmail(userDto.getEmail(), subject, body);
		}

		return true;
	}

	@Override
	public boolean resetPassword(PasswordDTO passwordDto) {
		UserEntity entity = userRepo.findByEmail(passwordDto.getEmail());

		entity.setPassword(passwordDto.getNewPassword());
		entity.setUpdatedPassword("YES");

		UserEntity saved = userRepo.save(entity);

		return saved != null;
	}

	@Override
	public QuoteResponseDTO getQuotation() {

		String apiUrl = "https://dummyjson.com/quotes/random";

		RestTemplate rt = new RestTemplate();
		ResponseEntity<QuoteResponseDTO> forEntity = rt.getForEntity(apiUrl, QuoteResponseDTO.class);

		return forEntity.getBody();

	}

	private String generateRandomPassword() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		StringBuilder password = new StringBuilder();

		Random random = new Random();

		for (int i = 0; i < 5; i++) {
			int index = (int) (random.nextFloat() * chars.length());
			password.append(chars.charAt(index));
		}

		String save = password.toString();

		return save;
	}

}
