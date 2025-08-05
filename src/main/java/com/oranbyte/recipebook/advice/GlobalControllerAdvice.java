package com.oranbyte.recipebook.advice;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.oranbyte.recipebook.dto.OrganizationSocialLinkDto;
import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.service.OrganizationSocialLinkService;
import com.oranbyte.recipebook.service.UserService;

@ControllerAdvice
public class GlobalControllerAdvice {
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private OrganizationSocialLinkService organizationSocialLinkService;

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
    public UserDto getLoggedInUser(Principal principal) {
    	if (principal == null) return null;
        return userService.getUserDto(principal.getName());
    }

    
    @ModelAttribute("organizationSocialLinks")
    public List<OrganizationSocialLinkDto> getOrganizationSocialLinks(){
    	return organizationSocialLinkService.getOrganizationSociaLinks();
    }
}
