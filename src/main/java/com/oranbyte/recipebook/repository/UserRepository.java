package com.oranbyte.recipebook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oranbyte.recipebook.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,  JpaSpecificationExecutor<User> {
	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String email);
	
	@Query(value = "SELECT DISTINCT u.* FROM users u JOIN recipe r ON u.id = r.user_id ORDER BY RAND() LIMIT 3", nativeQuery = true)
	List<User> findThreeRandomUsersWithRecipes();
	
	Optional<User> findByUsernameOrEmail(String username, String email);

	
}
