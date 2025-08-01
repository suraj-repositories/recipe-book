package com.oranbyte.recipebook.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.oranbyte.recipebook.dto.CommentDto;
import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.entity.Comment;
import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.mapper.CommentMapper;
import com.oranbyte.recipebook.repository.CommentRepository;
import com.oranbyte.recipebook.repository.RecipeRepository;
import com.oranbyte.recipebook.service.CommentService;
import com.oranbyte.recipebook.service.UserService;

@RestController
@RequestMapping("/recipe")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@PostMapping("/comment")
	public ResponseEntity<Map<String, String>> save(@ModelAttribute CommentDto dto) {
		Map<String, String> response = new HashMap<>();

	    try {
	        UserDto userDto = userService.getUser(2L);

	        Recipe recipe = recipeRepository.findById(dto.getRecipeId())
	    			.orElseThrow(() -> new RuntimeException("Recipe not found"));

	    		Comment comment = Comment.builder()
	    			.message(dto.getMessage())
	    			.recipe(recipe)
	    			.user(
	    				User.builder()
	    				.id(userDto.getId())
	    				.name(userDto.getName())
	    				.email(userDto.getEmail())
	    				.role(userDto.getRole())
	    				.image(userDto.getImage())
	    				.build()
	    			)
	    			.parent(dto.getParent() != null ? commentRepository.findById(dto.getParent().getId()).orElse(null) : null)
	    			.build();
	    		
	    		commentService.save(comment);

	        response.put("success", "Comment created successfully!");
	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("error", e.getMessage());
	        return ResponseEntity.badRequest().body(response);
	    }
	}
	@GetMapping("/comments")
	public ResponseEntity<Map<String, Object>> getComments(@RequestParam Long recipeId,
	                                                       @RequestParam int page) {

	    Pageable pageable = PageRequest.of(page, 3, Sort.by("createdAt").descending());

	    Page<Comment> commentPage = commentRepository.findByRecipeIdAndParentIsNull(recipeId, pageable);

	    List<CommentDto> commentDtos = commentPage.getContent()
	        .stream()
	        .map(CommentMapper::toDto)
	        .toList();

	    Context context = new Context();
	    context.setVariable("comments", commentDtos);

	    String html = templateEngine.process("recipes/comments-list", context);

	    Map<String, Object> response = new HashMap<>();
	    response.put("html", html);
	    response.put("isLastPage", commentPage.isLast());

	    return ResponseEntity.ok(response);
	}

	

	
}
