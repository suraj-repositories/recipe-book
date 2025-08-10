package com.oranbyte.recipebook.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oranbyte.recipebook.dto.CommentDto;
import com.oranbyte.recipebook.entity.Comment;
import com.oranbyte.recipebook.entity.Recipe;

public interface CommentService {

	Comment save(Comment comment);
	
	List<CommentDto> getComments(Recipe recipe);
	
	Page<CommentDto> searchComments(String userName, String recipeTitle, Pageable pageable);
	
}
