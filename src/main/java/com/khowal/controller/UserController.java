package com.khowal.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khowal.dto.PasswordDTO;
import com.khowal.dto.QuoteResponseDTO;
import com.khowal.dto.UserDTO;
import com.khowal.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String loadPage(Model model) {
		UserDTO userDTO = new UserDTO();
		model.addAttribute("user", userDTO);
		return "index";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("user") UserDTO userDto, Model model) {
		UserDTO login = userService.login(userDto.getEmail(), userDto.getPassword());
		if (login == null) {
			model.addAttribute("error", "Invalid credentials");
			return "index";
		}
		if (login.getUpdatedPassword().equals("YES")) {
			QuoteResponseDTO quotation = userService.getQuotation();
			model.addAttribute("quote", quotation);
			return "dashboard";
		} else {
			PasswordDTO password = new PasswordDTO();
			password.setEmail(login.getEmail());
			model.addAttribute("resetPassword", password);
			return "resetPassword";
		}
	}

	@GetMapping("/register")
	public String register(Model model) {

		UserDTO userDTO = new UserDTO();
		model.addAttribute("user", userDTO);

		Map<Integer, String> countriesMap = userService.getCountries();
		model.addAttribute("countries", countriesMap);

		return "register";
	}

	@GetMapping("/states/{countryId}")
	@ResponseBody
	public Map<Integer, String> getStates(@PathVariable Integer countryId) {
		return userService.getStates(countryId);
	}

	@GetMapping("/cities/{stateId}")
	@ResponseBody
	public Map<Integer, String> getCities(@PathVariable Integer stateId) {
		return userService.getCities(stateId);
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") UserDTO user, Model model) {
		boolean emailUnique = userService.isEmailUnique(user.getEmail());
		if (emailUnique) {
			boolean register = userService.register(user);
			if (register) {
				model.addAttribute("success", "User registered successfully");
			} else {
				model.addAttribute("error", "User registeration failed");
			}
		} else {
			model.addAttribute("error", "This Email Id already registered. Please enter unique Email Id");
		}
		Map<Integer, String> countriesMap = userService.getCountries();
		model.addAttribute("countries", countriesMap);
		return "register";
	}

	@PostMapping("/resetPassword")
	public String resetPassword(@ModelAttribute("passDto") PasswordDTO passDto, Model model) {

		UserDTO login = userService.login(passDto.getEmail(), passDto.getOldPassword());

		if (login == null) {
			model.addAttribute("error", "Old password is incorrect");
			return "resetPassword";

		}

		if (passDto.getNewPassword().equals(passDto.getConfirmPassword())) {
			userService.resetPassword(passDto);
			QuoteResponseDTO quotation = userService.getQuotation();
			model.addAttribute("quote", quotation);
			return "dashboard";
		} else {
			model.addAttribute("error", "New password doesn't matched with the confirm pasword");
			return "resetPassword";
		}
	}

	@GetMapping("/getQuote")
	public String getNewQuote(Model model) {
		QuoteResponseDTO quotation = userService.getQuotation();
		model.addAttribute("quote", quotation);
		return "dashboard";
	}

}
