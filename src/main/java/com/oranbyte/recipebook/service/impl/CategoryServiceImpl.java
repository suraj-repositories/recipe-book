package com.oranbyte.recipebook.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.dto.CategoryDto;
import com.oranbyte.recipebook.repository.CategoryRepository;
import com.oranbyte.recipebook.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<CategoryDto> getAllCategories() {
		return categoryRepository.findAll().stream()
		        .map(cat -> CategoryDto.builder()
		            .id(cat.getId())
		            .name(cat.getName())
		            .description(cat.getDescription())
		            .build())
		        .collect(Collectors.toList());
	}

}
