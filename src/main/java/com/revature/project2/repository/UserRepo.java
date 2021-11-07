package com.revature.project2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.project2.models.User;

@Repository
public interface UserRepo extends JpaRepository <User, Integer> {
	
	public User findByUsername (String username);
	
	public User findById (int id);

}
