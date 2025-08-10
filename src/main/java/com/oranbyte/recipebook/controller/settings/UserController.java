package com.oranbyte.recipebook.controller.settings;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings/user")
public class UserController {

	@GetMapping
	public String index(Model model) {
		
		
		return "settings/users-list";
	}
	
}
