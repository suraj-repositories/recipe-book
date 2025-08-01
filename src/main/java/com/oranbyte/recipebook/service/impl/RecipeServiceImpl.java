package com.oranbyte.recipebook.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.dto.RecipeDto;
import com.oranbyte.recipebook.entity.Category;
import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.mapper.RecipeMapper;
import com.oranbyte.recipebook.repository.CategoryRepository;
import com.oranbyte.recipebook.repository.RecipeRepository;
import com.oranbyte.recipebook.repository.UserRepository;
import com.oranbyte.recipebook.service.RecipeService;

@Service
public class RecipeServiceImpl implements RecipeService{

	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Recipe saveRecipe(Recipe recipe) {
		if (recipe.getServings() <= 0) {
		    recipe.setServings(1);
		}
		recipe.setUser(userRepository.findByEmail("admin@gmail.com"));
	    return recipeRepository.save(recipe);
	}

	
	@Override
	public Recipe convertToEntity(RecipeDto dto, User user) {
	    Category category = categoryRepository.findById(dto.getCategoryId())
	                            .orElseThrow(() -> new IllegalArgumentException("Category not found"));

	    return Recipe.builder()
	            .id(dto.getId())
	            .title(dto.getTitle())
	            .description(dto.getDescription())
	            .instructions(dto.getInstructions())
	            .prepTime(dto.getPrepTime())
	            .cookTime(dto.getCookTime())
	            .servings(dto.getServings())
	            .difficulty(dto.getDifficulty())
	            .user(user)
	            .category(category)
	            .build();
	}


	@Override
	public List<RecipeDto> getLast20Recipes() {
		 List<Recipe> recipes = recipeRepository.findTop20ByOrderByIdDesc();

	        return recipes.stream()
	                .map(RecipeMapper::toDto)
	                .collect(Collectors.toList());
	}


	@Override
	public Page<RecipeDto> getAllRecipes(Pageable pageable) {
		Page<Recipe> recipePage = recipeRepository.findAll(pageable);
	    
	    List<RecipeDto> recipeDtos = recipePage.getContent().stream()
	        .map(RecipeMapper::toDto)
	        .toList();

	    return new PageImpl<>(recipeDtos, pageable, recipePage.getTotalElements());
	}


	@Override
	public Page<RecipeDto> recentRecipes(Pageable pageable) {
	    return recipeRepository.findByOrderByIdDesc(pageable)
	            .map(RecipeMapper::toDto);
	}


	
}
