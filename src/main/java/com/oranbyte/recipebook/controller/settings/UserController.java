package com.oranbyte.recipebook.controller.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.service.PaginationService;
import com.oranbyte.recipebook.service.RecipeReactionService;
import com.oranbyte.recipebook.service.RecipeService;
import com.oranbyte.recipebook.service.UserService;

@Controller
@RequestMapping("/settings/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PaginationService paginationService;
	
	@Autowired
	private RecipeReactionService recipeReactionService;
	
	@Autowired
	private RecipeService recipeService;

	@GetMapping
	public String index(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String search, Model model) {

		int pageIndex = Math.max(page - 1, 0);

		Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(Sort.Direction.DESC, "createdAt"));
		Page<UserDto> users = userService.searchUsers(search, pageable);
		
		users.getContent().forEach(user -> {
	        user.setLikeCount(recipeReactionService.getLikeCount(user.getId()));
	        user.setDislikeCount(recipeReactionService.getDislikeCount(user.getId()));
	        user.setRecipeCount(recipeService.getRecipeCount(user.getId()));
	    });

		model.addAttribute("users", users.getContent());
		model.addAllAttributes(paginationService.getPageMetadata(users, page));
		model.addAttribute("currentPageDisplay", page);

		return "settings/users-list";
	}

}
