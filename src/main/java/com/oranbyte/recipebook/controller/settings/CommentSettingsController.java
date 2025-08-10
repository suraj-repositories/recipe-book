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

import com.oranbyte.recipebook.dto.CommentDto;
import com.oranbyte.recipebook.service.CommentService;
import com.oranbyte.recipebook.service.PaginationService;

@Controller
@RequestMapping("/settings/comments")
public class CommentSettingsController {
	
	@Autowired
	private CommentService commentService;
	
	
	@Autowired
	private PaginationService paginationService;

	@GetMapping
	public String index(
			Model model,
			@RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String search
		) {
		
		int pageIndex = Math.max(page - 1, 0);
		Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(Sort.Direction.DESC, "createdAt"));
		Page<CommentDto> commentPage = commentService.searchComments(search, search, pageable);
		
		model.addAttribute("comments", commentPage.getContent());
		model.addAllAttributes(paginationService.getPageMetadata(commentPage, page));
		model.addAttribute("currentPageDisplay", page);
		
		return "settings/comment-list";
	}
	
	
}
