package com.oranbyte.recipebook.mapper;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.oranbyte.recipebook.dto.UserDto;
import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.entity.UserDetail;

public class UserMapper {

	public static UserDto toDto(User user) {
		return UserDto.builder()
				.id(user.getId())
				.name(user.getName())
				.username(user.getUsername())
				.email(user.getEmail())
				.role(user.getRole())
				.image(user.getImageUrl())
				.bio(Optional.ofNullable(user.getUserDetail())
			             .map(UserDetail::getBio)
			             .orElse(null)
			    )
				.url(Optional.ofNullable(user.getUserDetail())
			             .map(UserDetail::getWebsiteUrl)
			             .orElse(null))
				.socialLinks(
						user.getSocialLinks()
					    .stream()
					    .map(UserSocialLinkMapper::toMap)
					    .flatMap(map -> map.entrySet().stream())
					    .collect(Collectors.toMap(
					        Map.Entry::getKey,
					        Map.Entry::getValue,
					        (existing, replacement) -> replacement
					    )))
				.build();
	}
	
}
