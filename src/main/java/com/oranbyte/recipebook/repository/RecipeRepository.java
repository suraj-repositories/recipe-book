package com.oranbyte.recipebook.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

	List<Recipe> findTop20ByOrderByIdDesc();

	Page<Recipe> findByOrderByIdDesc(Pageable pageable);

}
