package com.oranbyte.recipebook.service;

import java.util.Set;

import com.oranbyte.recipebook.entity.Tag;

public interface TagService {

	Set<Tag> saveAll(String [] tagNames);
	
	
}
