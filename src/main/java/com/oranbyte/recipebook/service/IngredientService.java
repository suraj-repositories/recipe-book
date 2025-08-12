package com.oranbyte.recipebook.service;

import com.oranbyte.recipebook.entity.Recipe;

public interface IngredientService {

	void saveRecipeIngredients(Recipe recipe, String[] names, Integer[] quantities, Long[] unitIds, String[] notes);

	void deleteByRecipe(Recipe recipe);
	
}
