package com.oranbyte.recipebook.service;

import java.util.List;

import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.entity.User;


public interface UserService {

	UserDto getUser(Long id);
	
	User save(User user);
	
	List<UserDto> getThreeRandomUsersWithRecipes();
	
	boolean existsByEmail(String email);
	
}
