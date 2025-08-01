package com.oranbyte.recipebook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.dto.UserDetailDto;
import com.oranbyte.recipebook.mapper.UserDetailMapper;
import com.oranbyte.recipebook.repository.UserDetailRepository;
import com.oranbyte.recipebook.service.UserDetailService;

@Service
public class UserDetailServiceImpl implements UserDetailService{

	@Autowired
	private UserDetailRepository userDetailRepository;
	
	@Override
	public UserDetailDto getUserDetailDto(Long userId) {
		return userDetailRepository.findByUserId(userId)
				.map(UserDetailMapper::toDto)
				.orElse(new UserDetailDto());
		
	}

}
