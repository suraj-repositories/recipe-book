package com.oranbyte.recipebook.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	private String image;

	private String role;

	public String getImageUrl() {
		if (image == null || image.trim().isEmpty()) {
			return "/images/john-doe.jpg";
		} else {
			String uploadDir = System.getProperty("user.dir") + "/uploads/";
			File file = new File(uploadDir + image.trim());
			return file.exists() ? "/uploads/" + image.trim() : "/images/john-doe.jpg";
		}
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Recipe> recipes = new ArrayList<>();

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private UserDetail userDetail;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserSocialLink> socialLinks = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RecipeReaction> reactions = new ArrayList<>();

	@ManyToMany
	@JoinTable(
		name = "user_followers", 
		joinColumns = @JoinColumn(name = "follower_id"), 
		inverseJoinColumns = @JoinColumn(name = "following_id")
	)
	private Set<User> following = new HashSet<>();

	@ManyToMany(mappedBy = "following")
	private Set<User> followers = new HashSet<>();

	public void follow(User user) {
		this.following.add(user);
		user.getFollowers().add(this);
	}

	public void unfollow(User user) {
		this.following.remove(user);
		user.getFollowers().remove(this);
	}
	
	public boolean isFollowing(User user) {
		return following.contains(user);
	}
	
	public boolean isFollowedBy(User user) {
        return followers.contains(user);
    }
	
}
