package com.oranbyte.recipebook.controller.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oranbyte.recipebook.dto.RecipeDto;
import com.oranbyte.recipebook.service.PaginationService;
import com.oranbyte.recipebook.service.RecipeService;

@Controller
@RequestMapping("/settings/recipes")
public class RecipeSettingsController {
	

	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private PaginationService paginationService;
	

	@GetMapping
	public String index(
			Model model,
			@RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) Long categoryId, 
			@RequestParam(required = false) Long tagId,
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String difficulty
	) {
		int pageIndex = Math.max(page - 1, 0);
		Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(Sort.Direction.DESC, "createdAt"));
		Page<RecipeDto> recipePage = recipeService.searchRecipes(categoryId, tagId, title, difficulty, pageable);
		
		model.addAttribute("recipes", recipePage.getContent());
		model.addAllAttributes(paginationService.getPageMetadata(recipePage, page));
		model.addAttribute("currentPageDisplay", page);
		return "settings/recipe-list";
	}
	
}
