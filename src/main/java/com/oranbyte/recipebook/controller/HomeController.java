package com.oranbyte.recipebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oranbyte.recipebook.dto.RecipeDto;
import com.oranbyte.recipebook.service.RecipeService;

@Controller
@RequestMapping(path = "/")
public class HomeController {
	
	@Autowired
	private RecipeService recipeService;
	
	@GetMapping
	public String index(Model model, Authentication auth) {
		System.out.println(auth.getName());
		
		List<RecipeDto> recipes = recipeService.getLast20Recipes();
			
		model.addAttribute("recipes", recipes);
		
		
		return "index";
	}

}
