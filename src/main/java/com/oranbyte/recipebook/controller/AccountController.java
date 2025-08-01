package com.oranbyte.recipebook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings/account")
public class AccountController {
	
	@GetMapping
	public String index() {
		return "settings/account";
	}
	
}
