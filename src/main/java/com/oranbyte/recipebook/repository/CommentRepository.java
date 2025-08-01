package com.oranbyte.recipebook.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.oranbyte.recipebook.entity.Comment;
import com.oranbyte.recipebook.entity.Recipe;

public interface CommentRepository extends JpaRepository<Comment, Long>{

	Page<Comment> findByRecipeIdAndParentIsNull(Long recipeId, Pageable pageable);
	
	List<Comment> findByRecipe(Recipe recipe);

}
