package com.oranbyte.recipebook.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.Category;
import com.oranbyte.recipebook.entity.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {

	List<Recipe> findTop20ByOrderByIdDesc();

	Page<Recipe> findByOrderByIdDesc(Pageable pageable);
	
	long countByCategory(Category category);
	
	@Query("SELECT COUNT(r) FROM Recipe r WHERE r.user.id = :userId")
	int countRecipesByUserId(@Param("userId") Long userId);
	
	

}
