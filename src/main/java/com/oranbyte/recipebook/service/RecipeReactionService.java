package com.oranbyte.recipebook.service;

import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.enums.ReactionType;

public interface RecipeReactionService {
	
	public void reactToRecipe(User user, Recipe recipe, ReactionType type);
	
	public long getLikeCount(Recipe recipe);
	
	public long getDislikeCount(Recipe recipe);
	
	public long getLikeCountByRecipeId(Long recipeId);
	
	public long getDislikeCountByRecipeId(long recipeId);
	
	public long getLikeCountByUserId(Long userId);
	
	public long getDislikeCountByUserId(Long userId);

	public boolean isReacted(User user, Recipe recipe, ReactionType type);

}
