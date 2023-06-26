package com.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.news.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.userName=?1")
	User getUserByUsername(String username);

}
