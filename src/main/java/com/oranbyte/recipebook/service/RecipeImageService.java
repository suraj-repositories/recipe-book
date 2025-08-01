package com.oranbyte.recipebook.service;

import org.springframework.web.multipart.MultipartFile;

import com.oranbyte.recipebook.entity.Recipe;

public interface RecipeImageService {

	public void saveAll(Recipe recipe, MultipartFile[] images);
	
}
