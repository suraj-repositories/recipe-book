package com.oranbyte.recipebook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.RecipeReaction;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.enums.ReactionType;

@Repository
public interface RecipeReactionRepository extends JpaRepository<RecipeReaction, Long>{

	Optional<RecipeReaction> findByUserAndRecipe(User user, Recipe recipe);
	
	long countByRecipeAndReactionType(Recipe recipe, ReactionType type);
	
}
