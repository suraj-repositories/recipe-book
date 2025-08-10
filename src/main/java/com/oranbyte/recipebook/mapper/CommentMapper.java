package com.oranbyte.recipebook.mapper;

import java.util.List;

import com.oranbyte.recipebook.dto.CommentDto;
import com.oranbyte.recipebook.entity.Comment;

public class CommentMapper {
	public static CommentDto toDto(Comment comment) {
	    return CommentDto.builder()
	            .id(comment.getId())
	            .message(comment.getMessage())
	            .createdAt(comment.getCreatedAt())
	            .user(UserMapper.toDto(comment.getUser()))
	            .recipe(RecipeMapper.toDto(comment.getRecipe()))
	            .parent(comment.getParent() != null ? toShallowDto(comment.getParent()) : null)
	            .replies(comment.getReplies() != null
		            ? comment.getReplies().stream()
		                .map(CommentMapper::toShallowDto)
		                .toList()
		            : List.of())
	            .build();
	}


    public static CommentDto toShallowDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .message(comment.getMessage())
                .build();
    }
}
