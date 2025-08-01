package com.oranbyte.recipebook.advice;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

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
}
