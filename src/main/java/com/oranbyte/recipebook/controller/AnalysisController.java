package com.oranbyte.recipebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.enums.ReactionType;
import com.oranbyte.recipebook.service.RecipeReactionService;
import com.oranbyte.recipebook.service.UserService;

@Controller
@RequestMapping("/settings/analysis")
public class AnalysisController {

	@Autowired
	private RecipeReactionService reactionService;

	@Autowired
	private UserService userService;

	@GetMapping
	public String index(Model model) {

		User user = userService.getLoginUser();
		model.addAttribute("weeklyLike", reactionService.getWeeklyCount(user.getId(), ReactionType.LIKE));
		model.addAttribute("weeklyDislike", reactionService.getWeeklyCount(user.getId(), ReactionType.DISLIKE));

		model.addAttribute("monthlyLike", reactionService.getMonthlyCount(user.getId(), ReactionType.LIKE));
		model.addAttribute("monthlyDislike", reactionService.getMonthlyCount(user.getId(), ReactionType.DISLIKE));

		model.addAttribute("yearlyLike", reactionService.getYearlyCount(user.getId(), ReactionType.LIKE));
		model.addAttribute("yearlyDislike", reactionService.getYearlyCount(user.getId(), ReactionType.DISLIKE));

		return "settings/analysis/analysis";
	}

}
