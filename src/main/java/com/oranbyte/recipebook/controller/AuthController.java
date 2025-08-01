package com.oranbyte.recipebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.service.UserService;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder encoder;

	@GetMapping("/login")
	public String loginPage(Model model, Authentication authentication) {
		if (authentication != null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return "auth/login";
	}

	@GetMapping("/register")
	public String registerPage(Model model, Authentication authentication) {
		if (authentication != null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		model.addAttribute("userDto", new UserDto());
		return "auth/register";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute UserDto userDto, Model model, Authentication authentication, RedirectAttributes redirectAttr) {
		if (authentication != null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		if (userService.existsByEmail(userDto.getEmail())) {
			model.addAttribute("error", "Email is already in use.");
			model.addAttribute("userDto", userDto);
			return "auth/register";
		}

		userDto.setRole("user");
		userService.save(User.builder()
			.name(userDto.getName())
			.email(userDto.getEmail())
			.password(encoder.encode(userDto.getPassword()))
			.role("user")
			.build());

		redirectAttr.addFlashAttribute("success", "Registration successful. Please login.");
		return "redirect:/login";
	}


}
