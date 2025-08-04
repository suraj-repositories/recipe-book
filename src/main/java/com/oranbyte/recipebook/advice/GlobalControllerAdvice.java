package com.oranbyte.recipebook.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.service.UserService;

@ControllerAdvice
public class GlobalControllerAdvice {
	
	@Autowired 
	private UserService userService;

    @ModelAttribute
    public void addUserInfoToModel(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isAuthenticated = auth != null &&
                                   auth.isAuthenticated() &&
                                   !"anonymousUser".equals(auth.getPrincipal());

        model.addAttribute("isAuthenticated", isAuthenticated);

        if (isAuthenticated) {
            Object principal = auth.getPrincipal();

            if (principal instanceof UserDetails userDetails) {
                model.addAttribute("currentUser", userDetails);
                model.addAttribute("username", userDetails.getUsername());
                model.addAttribute("roles", userDetails.getAuthorities());
            } else {
                model.addAttribute("username", principal.toString());
            }
        }
    }
    
    @ModelAttribute("authUser")
    public UserDto getLoggedInUser(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        if (principal == null) return null;

        return userService.getUserDto(principal.getUsername());
    }
}
