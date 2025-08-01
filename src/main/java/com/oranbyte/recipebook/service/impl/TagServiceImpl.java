package com.oranbyte.recipebook.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.entity.Tag;
import com.oranbyte.recipebook.repository.RecipeTagRepository;
import com.oranbyte.recipebook.repository.TagRepository;
import com.oranbyte.recipebook.service.TagService;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private RecipeTagRepository recipeTagRepository;

	@Override
	public Set<Tag> saveAll(String[] tagNames) {
		Set<Tag> tagSet = new HashSet<>();

		for (String name : tagNames) {
			String cleanedName = name.trim().toLowerCase();

			Tag tag = tagRepository.findByName(cleanedName)
					.orElseGet(() -> tagRepository.save(new Tag(null, cleanedName)));

			tagSet.add(tag);
		}

		return tagSet;
	}


}
