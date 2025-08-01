package com.oranbyte.recipebook.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.entity.SocialMediaPlatform;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.entity.UserDetail;
import com.oranbyte.recipebook.entity.UserSocialLink;
import com.oranbyte.recipebook.repository.SocialMediaPlatformRepository;
import com.oranbyte.recipebook.repository.UserRepository;
import com.oranbyte.recipebook.service.AuthService;
import com.oranbyte.recipebook.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/settings/profile")
public class ProfileController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private SocialMediaPlatformRepository socialMediaPlatformRepository;
	
	@GetMapping
	public String index(Model model, Principal principal) {
		String email = principal.getName();
	    User repoUser = userRepository.findByEmail(email);
	    UserDto user = userService.getUser(repoUser.getId());
		model.addAttribute("profileuser", user);
		
		return "settings/profile";
	}
	
	@PostMapping("/update")
	public String updateProfile(@ModelAttribute UserDto form, Principal principal, HttpServletRequest request, RedirectAttributes redirectAttributes) {
	   
		String email = principal.getName();
	    User user = userRepository.findByEmail(email);
	    
	    user.setName(form.getName());
	    user.setEmail(form.getEmail());

	    if (user.getUserDetail() == null) {
	        user.setUserDetail(new UserDetail());
	        user.getUserDetail().setUser(user);
	    }
	    user.getUserDetail().setBio(form.getBio());
	    user.getUserDetail().setWebsiteUrl(form.getUrl());

	    List<UserSocialLink> updatedLinks = new ArrayList<>();
	    form.getSocialLinks().forEach((platformName, url) -> {
	        if (url != null && !url.isBlank()) {
	            SocialMediaPlatform platform = socialMediaPlatformRepository.findByNameIgnoreCase(platformName)
	                .orElseThrow(() -> new RuntimeException("Platform not found: " + platformName));
	            updatedLinks.add(UserSocialLink.builder()
	                .user(user)
	                .platform(platform)
	                .url(url)
	                .build());
	        }
	    });

	    user.getSocialLinks().clear();
	    user.getSocialLinks().addAll(updatedLinks);


	    userRepository.save(user);
	    authService.login(user);
	    redirectAttributes.addFlashAttribute("success", "Your profile updated successfully!");
	    return "redirect:" + request.getHeader("Referer");
	}

	
	
}
