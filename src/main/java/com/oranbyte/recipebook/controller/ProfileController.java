package com.oranbyte.recipebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oranbyte.recipebook.dto.RecipeDto;
import com.oranbyte.recipebook.service.RecipeService;

@Controller
@RequestMapping("/u")
public class ProfileController {

	@Autowired
	private RecipeService recipeService;
	
	
	@GetMapping("/{username}")
	public String index(@PathVariable String username, Model model) {
		List<RecipeDto> recipes = recipeService.getLast20Recipes();
		model.addAttribute("recipes", recipes);
		System.out.println(recipes.size());
		return "profile/user-profile";
	}
	
}
