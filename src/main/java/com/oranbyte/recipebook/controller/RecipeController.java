package com.oranbyte.recipebook.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oranbyte.recipebook.dto.CommentDto;
import com.oranbyte.recipebook.dto.RecipeDto;
import com.oranbyte.recipebook.entity.Recipe;
import com.oranbyte.recipebook.entity.Tag;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.mapper.RecipeMapper;
import com.oranbyte.recipebook.mapper.UserMapper;
import com.oranbyte.recipebook.repository.UserRepository;
import com.oranbyte.recipebook.service.CategoryService;
import com.oranbyte.recipebook.service.IngredientService;
import com.oranbyte.recipebook.service.RecipeImageService;
import com.oranbyte.recipebook.service.RecipeService;
import com.oranbyte.recipebook.service.TagService;
import com.oranbyte.recipebook.service.UnitService;
import com.oranbyte.recipebook.service.UserDetailService;
import com.oranbyte.recipebook.service.UserService;
import com.oranbyte.recipebook.service.UserSocialLinksService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private RecipeImageService recipeImageService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private UserSocialLinksService userSocialLinksService;
	

	@GetMapping
	public String index(@RequestParam(defaultValue = "0") int page,
	                    @RequestParam(defaultValue = "15") int size,
	                    Model model) {
	    
		 Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
		 Page<RecipeDto> recipePage = recipeService.getAllRecipes(pageable);

	    model.addAttribute("recipes", recipePage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", recipePage.getTotalPages());
		
		
		return "recipes/recipes";
	}

	@GetMapping("create")
	public String create(Model model) {
		model.addAttribute("units", unitService.getAll());
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("recipeDto", new RecipeDto());
		
		return "recipes/create-recipe";
	}

	@PostMapping("/save")
    public String saveRecipe(@ModelAttribute RecipeDto recipeDto,
                             @RequestParam(value = "images" , required = false) MultipartFile[] images,
                             @RequestParam(value = "tags", required =  false) String[] tags,
                             @RequestParam(value = "ingredient_names", required = false) String[] ingredientNames,
                             @RequestParam(value = "ingredient_quantities", required = false) Integer[] ingredientQuantities,
                             @RequestParam(value = "ingredient_units", required = false) Long[] ingredientUnitIds,
                             @RequestParam(value = "ingredient_notes", required = false) String[] ingredientNotes,
                             RedirectAttributes redirectAttr,
                             HttpServletRequest request) {

        try {
            User user = userRepository.findByEmail("admin@gmail.com");
            Recipe recipe = recipeService.convertToEntity(recipeDto, user);
            
            if(tags.length > 0) {
            	Set<Tag> savedTags = tagService.saveAll(tags);
            	recipe.setTags(savedTags);            	
            }
            
            Recipe savedRecipe = recipeService.saveRecipe(recipe);
            
            if(ingredientNames.length > 0) {
            	ingredientService.saveRecipeIngredients(savedRecipe, ingredientNames, ingredientQuantities, ingredientUnitIds, ingredientNotes);            	
            }

            if(images.length > 0) {
            	recipeImageService.saveAll(savedRecipe, images);            	
            }

            redirectAttr.addFlashAttribute("success", "Your recipe created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttr.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:" + request.getHeader("Referer");
    }
	
	@GetMapping("/{recipe}")
	public String show(@PathVariable Recipe recipe, Model model) {
		model.addAttribute("recipe", RecipeMapper.toDto(recipe));
		model.addAttribute("user", UserMapper.toDto(recipe.getUser()));
		model.addAttribute("comment_dto", CommentDto.builder().recipeId(recipe.getId()).build());
		model.addAttribute("user_detail", userDetailService.getUserDetailDto(recipe.getUser().getId()));
		model.addAttribute("user_social_links", userSocialLinksService.getUserSocialLinks(recipe.getUser().getId()));
		model.addAttribute("authors", userService.getThreeRandomUsersWithRecipes());
		
		model.addAttribute("tags", recipe.getTags());
		model.addAttribute("recent_recipies", recipeService.recentRecipes(PageRequest.of(0, 3)));
		 return "recipes/recipe-details";
	}


}
