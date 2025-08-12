package com.oranbyte.recipebook.service;

import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.enums.ReactionType;

public interface RecipeReactionService {
	
	public void reactToRecipe(User user, Recipe recipe, ReactionType type);
	
	public long getLikeCount(Recipe recipe);
	
	public long getDislikeCount(Recipe recipe);
	
	public long getLikeCount(long recipeId);
	
	public long getDislikeCount(long recipeId);
	
	public boolean isReacted(User user, Recipe recipe, ReactionType type);

}
