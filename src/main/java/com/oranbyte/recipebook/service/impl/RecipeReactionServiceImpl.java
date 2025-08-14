package com.oranbyte.recipebook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.RecipeReaction;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.enums.ReactionType;
import com.oranbyte.recipebook.repository.RecipeReactionRepository;
import com.oranbyte.recipebook.service.RecipeReactionService;

@Service
public class RecipeReactionServiceImpl implements RecipeReactionService{

	@Autowired
	private RecipeReactionRepository recipeReactionRepository;
	
	@Override
	public void reactToRecipe(User user, Recipe recipe, ReactionType type) {
		recipeReactionRepository.findByUserAndRecipe(user, recipe)
		.ifPresentOrElse(reaction -> {
			
			
			if(reaction.getReactionType() == type) {
				recipeReactionRepository.delete(reaction);
			}else {
				reaction.setReactionType(type);
				recipeReactionRepository.save(reaction);
			}
		}, 
		()->{
			RecipeReaction reaction = RecipeReaction.builder()
					.user(user)
					.recipe(recipe)
					.reactionType(type)
					.build();
			recipeReactionRepository.save(reaction);
		});
	}

	@Override
	public long getLikeCount(Recipe recipe) {
		return recipeReactionRepository.countByRecipeAndReactionType(recipe, ReactionType.LIKE);
	}

	@Override
	public long getDislikeCount(Recipe recipe) {
		return recipeReactionRepository.countByRecipeAndReactionType(recipe, ReactionType.DISLIKE);
	}
	
	public boolean isReacted(User user, Recipe recipe, ReactionType type) {
	    return recipeReactionRepository.findByUserAndRecipe(user, recipe)
	            .map(r -> r.getReactionType() == type)
	            .orElse(false);
	}

	@Override
	public long getLikeCount(long recipeId) {
		return recipeReactionRepository.countByRecipeIdAndReactionType(recipeId, ReactionType.LIKE);
	}

	@Override
	public long getDislikeCount(long recipeId) {
		return recipeReactionRepository.countByRecipeIdAndReactionType(recipeId, ReactionType.DISLIKE);
	}

	@Override
	public long getLikeCount(Long userId) {
		return recipeReactionRepository.countByUserIdAndReactionType(userId, ReactionType.LIKE);
	}

	@Override
	public long getDislikeCount(Long userId) {
		return recipeReactionRepository.countByUserIdAndReactionType(userId, ReactionType.DISLIKE);
	}

}
