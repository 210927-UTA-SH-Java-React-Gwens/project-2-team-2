package com.revature.project2.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project2.models.User;
import com.revature.project2.repository.UserRepo;

@Service
public class UserService {

	private UserRepo uDao;
	
	@Autowired
	public UserService(UserRepo u) {
		this.uDao = u;
	}
	
	public User createUser (User u) {
		try {
			uDao.save(u);
			return u;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public User findUserByUsername(String username) {
		return uDao.findByUsername(username);
	}
	
	public List<User> getAllUsers(){
		try {
			return uDao.findAll();
		} catch (Exception e) {
			return new ArrayList<User>();
		}
	}
	
	public User getUser(int id) {
	
		try {
			return uDao.findById(id);
		}catch (Exception e) {
			return null;
		}
		
	}
	
	
	
}
