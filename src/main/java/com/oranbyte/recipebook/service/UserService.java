package com.oranbyte.recipebook.service;

import java.util.List;

import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.entity.User;


public interface UserService {
	
	User getUser(String username);
	
	User getUser(Long id);

	UserDto getUserDto(String username);
	
	UserDto getUserDto(Long id);
	
	User save(User user);
	
	List<UserDto> getThreeRandomUsersWithRecipes();
	
	boolean existsByEmail(String email);
	
	User getLoginUser();
	
}
