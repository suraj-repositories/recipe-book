package com.oranbyte.recipebook.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.mapper.UserMapper;
import com.oranbyte.recipebook.repository.UserRepository;
import com.oranbyte.recipebook.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDto getUser(Long id) {
		return userRepository.findById(id).map(UserMapper::toDto).orElse(null);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<UserDto> getThreeRandomUsersWithRecipes() {
		return userRepository.findThreeRandomUsersWithRecipes().stream().map(UserMapper::toDto).toList();
	}
	
	@Override
	public boolean existsByEmail(String email) {
		return userRepository.findByEmail(email) != null;
	}


	@Override
	public User getUser(String username) {
		return userRepository.findByUsernameOrEmail(username, username).orElse(null);
	}


	@Override
	public UserDto getUserDto(String username) {
		return userRepository.findByUsername(username).map(UserMapper::toDto).orElse(null);
	}


}
