package com.oranbyte.recipebook.service;

import java.util.List;

import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.entity.User;


public interface UserService {
	
	User getUser(String username);

	UserDto getUserDto(String username);
	
	UserDto getUser(Long id);
	
	User save(User user);
	
	List<UserDto> getThreeRandomUsersWithRecipes();
	
	boolean existsByEmail(String email);
	
}
